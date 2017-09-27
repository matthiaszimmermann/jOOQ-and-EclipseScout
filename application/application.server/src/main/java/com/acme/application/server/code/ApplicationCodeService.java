package com.acme.application.server.code;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.CodeRow;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipse.scout.rt.shared.services.common.code.ICodeRow;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.or.core.tables.Code;
import com.acme.application.database.or.core.tables.records.CodeRecord;
import com.acme.application.database.or.core.tables.records.TextRecord;
import com.acme.application.server.ServerSession;
import com.acme.application.server.common.AbstractBaseService;
import com.acme.application.server.text.TextService;
import com.acme.application.shared.code.ApplicationCodeFormData;
import com.acme.application.shared.code.ApplicationCodePageData;
import com.acme.application.shared.code.ApplicationCodePageData.ApplicationCodeRowData;
import com.acme.application.shared.code.ApplicationCodeUtility;
import com.acme.application.shared.code.IApplicationCodeService;
import com.acme.application.shared.code.IApplicationCodeType;
import com.acme.application.shared.code.ReadApplicationCodePermission;
import com.acme.application.shared.code.UpdateApplicationCodePermission;

public class ApplicationCodeService extends AbstractBaseService<Code, CodeRecord> implements IApplicationCodeService {

	@Override
	public Code getTable() {
		return Code.CODE;
	}

	@Override
	public Field<String> getIdColumn() {
		return Code.CODE.ID;
	}

	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger(ApplicationCodeService.class);
	}

	@Override
	public ApplicationCodePageData getApplicationCodeTableData(Class<? extends IApplicationCodeType> codeTypeName) {
		ApplicationCodePageData pageData = new ApplicationCodePageData();
		IApplicationCodeType codeType = ApplicationCodeUtility.getCodeType(codeTypeName);
		Locale locale = ServerSession.get().getLocale();

		// enforce reload from database
		loadCodeRowsFromDatabase(codeType.getId())
		.stream()
		.forEach(code -> {
			String codeId = code.getKey();
			ApplicationCodeRowData row = pageData.addRow();	
			row.setId(codeId);
			row.setType(TEXTS.get(locale, codeType.getId(), codeType.getId()));
			row.setText(TEXTS.get(locale, codeId, codeId));
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
			formData.getOrder().setValue(BigDecimal.valueOf(code.getOrder()));
			formData.getActive().setValue(code.isActive());
		}
		else {
			formData.getCodeId().setValue(ApplicationCodeUtility.generateCodeId());
			formData.getOrder().setValue(BigDecimal.ZERO);
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
		double order = (double) ObjectUtility.nvl(formData.getOrder().getValue().doubleValue(), 0.0);
		String icon = null;
		boolean active = formData.getActive().getValue();

		return new CodeRow<String>(id, text)
				.withOrder(order)
				.withIconId(icon)
				.withActive(active);
	}

	private void refresh(String codeTypeId) {
		IApplicationCodeType codeType = ApplicationCodeUtility.getCodeType(codeTypeId);
		ApplicationCodeUtility.reload(codeType.getCodeTypeClass());
	}

	private CodeRecord toCodeRecord(String codeTypeId, ICodeRow<String> codeRow) {
		double order = codeRow.getOrder();
		String icon = null;
		String value = null;
		boolean active = codeRow.isActive();
		return new CodeRecord(codeRow.getKey(), codeTypeId, order, icon, value, active);
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
		try(Connection connection = getConnection()) {
			DSLContext context = getContext(connection);

			if(dynamicCodeExists(context, code.getTypeId(), code.getId())) { 
				context.executeUpdate(code); 
			}
			else { 
				context.executeInsert(code); 
			}
		} 
		catch (SQLException e) {
			getLogger().error("Failed to execute store(). code: {}. exception: ", code, e);
		}
	}

	/**
	 * Returns true iff the dynamic code specified by the provided id and code type id exists.
	 */
	private boolean dynamicCodeExists(DSLContext context, String codeTypeId, String codeId) {
		return context.fetchExists(
				context.select()
				.from(getTable())
				.where(getTable().TYPE_ID.eq(codeTypeId)
						.and(getTable().ID.eq(codeId))));
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
		getLogger().info("(Re)load dynamic codes from database for code id " + codeTypeId);
		Locale locale = ServerSession.get().getLocale();

		return getCodeRecords(codeTypeId)
				.stream()
				.map(code -> {
					String id = code.getId();
					String text = TEXTS.get(locale, id, id);
					double order = code.getOrder() != null ? code.getOrder() : 0.0;
					return new CodeRow<String>(id, text)
							.withOrder(order)
							.withIconId(code.getIcon())
							.withActive(code.getActive());
				})
				.collect(Collectors.toList());
	}

	/**
	 * Loads all dynamic code rows from the database for the provided code type id.
	 */
	public List<CodeRecord> getCodeRecords(String codeTypeId) {
		try(Connection connection = getConnection()) {
			return getContext(connection)
					.selectFrom(getTable())
					.where(getTable().TYPE_ID.eq(codeTypeId))
					.stream()
					.collect(Collectors.toList());
		} 
		catch (SQLException e) {
			getLogger().error("Failed to execute getCodeRecords(). codeTypeId: {}. exception: ", codeTypeId, e);
		}

		return new ArrayList<CodeRecord>();
	}

	/**
	 * Loads the dynamic code from the database specified for the provided code type id and code id.
	 */
	public CodeRecord getCodeRecord(String codeTypeId, String codeId) {
		try(Connection connection = getConnection()) {
			return getContext(connection)
					.selectFrom(getTable())
					.where(getTable().TYPE_ID.eq(codeTypeId)
							.and(getTable().ID.eq(codeId)))
					.fetchOne();
		} 
		catch (SQLException e) {
			getLogger().error("Failed to execute getCodeRecord(). codeTypeId: {}, codeId: {}. exception: ", codeTypeId, codeId, e);
		}

		return null;
	}

	/**
	 * Initializes dynamic codes for the provided code type class.
	 */
	public void reload(Class<? extends IApplicationCodeType> clazz) {
		ApplicationCodeUtility.reload(clazz);
	}
}
