package com.acme.application.server.code;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.CodeRow;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipse.scout.rt.shared.services.common.code.ICodeRow;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.generator.Config;
import com.acme.application.database.or.app.tables.Code;
import com.acme.application.database.or.app.tables.records.CodeRecord;
import com.acme.application.database.or.app.tables.records.TextRecord;
import com.acme.application.server.common.BaseService;
import com.acme.application.server.text.TextService;
import com.acme.application.shared.code.ApplicationCodeFormData;
import com.acme.application.shared.code.ApplicationCodePageData;
import com.acme.application.shared.code.ApplicationCodePageData.ApplicationCodeRowData;
import com.acme.application.shared.code.ApplicationCodeUtility;
import com.acme.application.shared.code.IApplicationCodeService;
import com.acme.application.shared.code.IApplicationCodeType;
import com.acme.application.shared.code.ReadApplicationCodePermission;
import com.acme.application.shared.code.UpdateApplicationCodePermission;

public class ApplicationCodeService extends BaseService implements IApplicationCodeService {

	private static final Logger LOG = LoggerFactory.getLogger(ApplicationCodeService.class);

	private Config config = null;

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	@Override
	public ApplicationCodePageData getApplicationCodeTableData(Class<? extends IApplicationCodeType> codeTypeName) {
		ApplicationCodePageData pageData = new ApplicationCodePageData();
		IApplicationCodeType codeType = ApplicationCodeUtility.getCodeType(codeTypeName);

		// enforce reload from database
		loadCodeRowsFromDatabase(codeType.getId())
		.stream()
		.forEach(code -> {
			String codeId = code.getKey();
			ApplicationCodeRowData row = pageData.addRow();	
			row.setId(codeId);
			row.setType(TEXTS.getWithFallback(codeType.getId(), codeType.getId()));
			row.setText(TEXTS.getWithFallback(codeId, codeId));
			row.setActive(code.isActive());
		});

		return pageData;
	}

	@Override
	public ApplicationCodeFormData load(ApplicationCodeFormData formData) {
		if (!ACCESS.check(new ReadApplicationCodePermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		String codeId = formData.getCodeId().getValue();

		if(codeId != null) {
			IApplicationCodeType codeType = ApplicationCodeUtility.getCodeType(formData.getCodeTypeId());
			ICode<String> code = ApplicationCodeUtility.getCode(codeType.getCodeTypeClass(), codeId);		
			formData.getCodeText().setValue(code.getText());
			formData.getActive().setValue(code.isActive());
		}
		else {
			formData.getCodeId().setValue(ApplicationCodeUtility.generateCodeId());
			formData.getActive().setValue(true);
		}

		return formData;
	}



	@Override
	public ApplicationCodeFormData store(ApplicationCodeFormData formData) {
		if (!ACCESS.check(new UpdateApplicationCodePermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		String codeTypeId = formData.getCodeTypeId();
		store(codeTypeId, toCodeRow(formData));
		refresh(codeTypeId);
		
		return formData;
	}

	private ICodeRow<String> toCodeRow(ApplicationCodeFormData formData) {
		String id = formData.getCodeId().getValue();
		String text = formData.getCodeText().getValue();
		String icon = null;
		boolean active = formData.getActive().getValue();
		
		return new CodeRow<String>(id, text)
				.withIconId(icon)
				.withActive(active);
	}

	private void refresh(String codeTypeId) {
		IApplicationCodeType codeType = ApplicationCodeUtility.getCodeType(codeTypeId);
		ApplicationCodeUtility.reload(codeType.getCodeTypeClass());
	}

	private CodeRecord toCodeRecord(String codeTypeId, ICodeRow<String> codeRow) {
		String icon = null;
		String value = null;
		return new CodeRecord(codeRow.getKey(), codeTypeId, icon, value, codeRow.isActive());
	}

	@Override
	public void store(String codeTypeId, ICodeRow<String> codeRow) {
		store(toCodeRecord(codeTypeId, codeRow));
		storeText(codeRow);
	}

	/**
	 * Persists the provided code.
	 */
	protected void store(CodeRecord code) {
		LOG.info("Persist code\n{}", code);

		if(dynamicCodeExists(code.getTypeId(), code.getId())) { 
			getContext().executeUpdate(code); 
		}
		else { 
			getContext().executeInsert(code); 
		}
	}

	/**
	 * Returns true iff the dynamic code specified by the provided id and code type id exists.
	 */
	private boolean dynamicCodeExists(String codeTypeId, String codeId) {
		Code ct = Code.CODE;
		DSLContext ctx = getContext();

		return ctx.fetchExists(
				ctx.select()
				.from(ct)
				.where(ct.TYPE_ID.eq(codeTypeId).and(ct.ID.eq(codeId))));
	}

	/**
	 * Persist default translation for code.
	 */
	private void storeText(ICodeRow<String> codeRow) {
		TextService service = BEANS.get(TextService.class); 
		service.store(new TextRecord(codeRow.getKey(), TextService.LOCALE_DEFAULT, codeRow.getText()));
		service.invalidateCache();
	}

	@Override
	public List<ICodeRow<String>> loadCodeRowsFromDatabase(String codeTypeId) {
		LOG.info("(Re)load dynamic codes from database for code id " + codeTypeId);

		return getCodeRecords(codeTypeId)
				.stream()
				.map(code -> {
					String id = code.getId();
					String text = TEXTS.getWithFallback(id, id);
					return new CodeRow<String>(id, text)
							.withIconId(code.getIcon())
							.withActive(code.getActive());
				})
				.collect(Collectors.toList());
	}

	/**
	 * Loads all dynamic code rows from the database for the provided code type id.
	 */
	public List<CodeRecord> getCodeRecords(String codeTypeId) {
		return getConfig().getContext()
				.selectFrom(Code.CODE)
				.where(Code.CODE.TYPE_ID.eq(codeTypeId))
				.stream()
				.collect(Collectors.toList());
	}

	/**
	 * Loads the dynamic code from the database specified for the provided code type id and code id.
	 */
	public CodeRecord getCodeRecord(String codeTypeId, String codeId) {
		Code ct = Code.CODE;
		return getConfig().getContext()
				.selectFrom(ct)
				.where(ct.TYPE_ID.eq(codeTypeId).and(ct.ID.eq(codeId)))
				.fetchOne();
	}

	/**
	 * Initializes dynamic codes for the provided code type class.
	 */
	public void reload(Class<? extends IApplicationCodeType> clazz) {
		ApplicationCodeUtility.reload(clazz);
	}
}
