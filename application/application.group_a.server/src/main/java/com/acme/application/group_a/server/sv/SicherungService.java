package com.acme.application.group_a.server.sv;

import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.jooq.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.or.sv.tables.Fi;
import com.acme.application.database.or.sv.tables.records.FiRecord;
import com.acme.application.database.table.TableUtility;
import com.acme.application.group_a.shared.sv.CreateSicherungPermission;
import com.acme.application.group_a.shared.sv.ISicherungService;
import com.acme.application.group_a.shared.sv.ReadSicherungPermission;
import com.acme.application.group_a.shared.sv.SicherungFormData;
import com.acme.application.group_a.shared.sv.SicherungTablePageData;
import com.acme.application.group_a.shared.sv.SicherungTablePageData.SicherungTableRowData;
import com.acme.application.group_a.shared.sv.UpdateSicherungPermission;
import com.acme.application.server.common.AbstractBaseService;

public class SicherungService extends AbstractBaseService<Fi, FiRecord> implements ISicherungService {

	@Override
	public Fi getTable() {
		return Fi.FI;
	}

	@Override
	public Field<String> getIdColumn() {
		return Fi.FI.ID;
	}

	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger(SicherungService.class);
	}

	@Override
	public SicherungFormData prepareCreate(SicherungFormData formData) {
		if (!ACCESS.check(new CreateSicherungPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		formData.setId(TableUtility.createId());
		formData.getActive().setValue(true);
		return formData;
	}

	@Override
	public SicherungFormData create(SicherungFormData formData) {
		if (!ACCESS.check(new CreateSicherungPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		store(formData.getId(), mapToRecord(formData)); 
		return formData;
	}

	@Override
	public SicherungFormData load(SicherungFormData formData) {
		if (!ACCESS.check(new ReadSicherungPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		FiRecord fi = get(formData.getId());
		return mapToFormData(formData, fi);
	}

	@Override
	public SicherungFormData store(SicherungFormData formData) {
		if (!ACCESS.check(new UpdateSicherungPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		store(formData.getId(), mapToRecord(formData)); 
		return formData;
	}

	@Override
	public SicherungTablePageData getSicherungTableData(SearchFilter filter, boolean activeOnly) {
		SicherungTablePageData pageData = new SicherungTablePageData();

		getAll()
		.stream()
		.filter(fi -> !activeOnly || fi.getActive())
		.forEach(fi -> addToPageData(pageData, fi));

		return pageData;
	}

	private void addToPageData(SicherungTablePageData pageData, FiRecord fi) {
		SicherungTableRowData row = pageData.addRow();
		row.setId(fi.getId());
		row.setGebaeude(fi.getGebNr());
		row.setAnlNr(fi.getAnlNr());
		row.setAnlage(fi.getAnlage());
		row.setEtage(fi.getEtage());
		row.setStandort(fi.getStandort());
		row.setSicherung(fi.getSicherung());
		row.setIf(fi.getIf());
		row.setBefund(fi.getBefund());
		row.setPruefmethode(fi.getPruefmet());
		row.setVisum(fi.getVisum());
		row.setDatum(fi.getDatum());
		row.setActive(fi.getActive());
	}

	private SicherungFormData mapToFormData(SicherungFormData formData, FiRecord fi) {
		formData.getGebNr().setValue(fi.getGebNr());
		formData.getAnlNr().setValue(fi.getAnlNr());
		formData.getAnlage().setValue(fi.getAnlage());
		formData.getEtage().setValue(fi.getEtage());
		formData.getStandort().setValue(fi.getStandort());
		formData.getSicherung().setValue(fi.getSicherung());
		formData.getIf().setValue(fi.getIf());
		formData.getBefund().setValue(fi.getBefund());
		formData.getPruefmethode().setValue(fi.getPruefmet());
		formData.getVisum().setValue(fi.getVisum());
		formData.getDatum().setValue(fi.getDatum());
		formData.getActive().setValue(fi.getActive());

		return formData;
	}

	private FiRecord mapToRecord(SicherungFormData formData) {
		Fi t = getTable();
		return new FiRecord()
				.with(t.ID, formData.getId())
				.with(t.GEB_NR, formData.getGebNr().getValue())
				.with(t.ANL_NR, formData.getAnlNr().getValue())
				.with(t.ANLAGE, formData.getAnlage().getValue())
				.with(t.ETAGE, formData.getEtage().getValue())
				.with(t.STANDORT, formData.getStandort().getValue())
				.with(t.SICHERUNG, formData.getSicherung().getValue())
				.with(t.IF, formData.getIf().getValue())
				.with(t.BEFUND, formData.getBefund().getValue())
				.with(t.PRUEFMET, formData.getPruefmethode().getValue())
				.with(t.VISUM, formData.getVisum().getValue())
				.with(t.DATUM, formData.getDatum().getValue())
				.with(t.ACTIVE, formData.getActive().getValue());
	}
}
