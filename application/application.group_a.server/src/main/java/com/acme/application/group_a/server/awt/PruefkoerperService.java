package com.acme.application.group_a.server.awt;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.jooq.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.or.awt.tables.Pruefkoerper;
import com.acme.application.database.or.awt.tables.records.ProduktRecord;
import com.acme.application.database.or.awt.tables.records.PruefkoerperRecord;
import com.acme.application.database.table.TableUtility;
import com.acme.application.group_a.shared.awt.CreatePruefkoerperPermission;
import com.acme.application.group_a.shared.awt.IPruefkoerperService;
import com.acme.application.group_a.shared.awt.PruefkoerperFormData;
import com.acme.application.group_a.shared.awt.PruefkoerperTablePageData;
import com.acme.application.group_a.shared.awt.PruefkoerperTablePageData.PruefkoerperTableRowData;
import com.acme.application.group_a.shared.awt.ReadPruefkoerperPermission;
import com.acme.application.group_a.shared.awt.UpdatePruefkoerperPermission;
import com.acme.application.server.common.AbstractBaseService;

public class PruefkoerperService extends AbstractBaseService<Pruefkoerper, PruefkoerperRecord>
implements IPruefkoerperService {

	@Override
	public Pruefkoerper getTable() {
		return Pruefkoerper.PRUEFKOERPER;
	}

	@Override
	public Field<String> getIdColumn() {
		return Pruefkoerper.PRUEFKOERPER.ID;
	}

	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger(PruefkoerperService.class);
	}

	@Override
	public PruefkoerperTablePageData getPruefkoerperTableData(SearchFilter filter, boolean activeOnly) {
		PruefkoerperTablePageData pageData = new PruefkoerperTablePageData();

		getAll()
		.stream()
		.filter(pruefkoerper -> !activeOnly || pruefkoerper.getActive())
		.forEach(pruefkoerper -> addToPageData(pageData, pruefkoerper));

		return pageData;
	}

	private void addToPageData(PruefkoerperTablePageData pageData, PruefkoerperRecord pruefkoerper) {
		ProduktRecord product = BEANS.get(ProduktService.class).get(pruefkoerper.getProduktId());

		PruefkoerperTableRowData row = pageData.addRow();
		row.setId(pruefkoerper.getId());
		row.setNr(pruefkoerper.getNr());
		row.setProdukt(pruefkoerper.getProduktId());
		row.setProduktFamilie(product.getFamilie());
		row.setForm(pruefkoerper.getForm());
		row.setColor(pruefkoerper.getColorId());
		row.setColorDescription(pruefkoerper.getColorDescription());
		row.setSchrank(pruefkoerper.getSchrank());
		row.setBoxe(pruefkoerper.getBoxe());
		row.setBreite(pruefkoerper.getBreite());
		row.setLaenge(pruefkoerper.getLaenge());
		row.setDicke(pruefkoerper.getDicke());
		row.setBestand(pruefkoerper.getBestand());
		row.setBestandMin(product.getBestandMin());
		row.setActive(pruefkoerper.getActive());
	}

	@Override
	public PruefkoerperFormData prepareCreate(PruefkoerperFormData formData) {
		if (!ACCESS.check(new CreatePruefkoerperPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		formData.setId(TableUtility.createId());
		formData.getForm().setValue("Platte");
		formData.getActive().setValue(true);
		return formData;
	}

	@Override
	public PruefkoerperFormData create(PruefkoerperFormData formData) {
		if (!ACCESS.check(new CreatePruefkoerperPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		store(formData.getId(), mapToRecord(formData)); 
		return formData;
	}

	@Override
	public PruefkoerperFormData load(PruefkoerperFormData formData) {
		if (!ACCESS.check(new ReadPruefkoerperPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		PruefkoerperRecord pruefkoerper = get(formData.getId());
		return mapToFormData(formData, pruefkoerper);
	}

	@Override
	public PruefkoerperFormData store(PruefkoerperFormData formData) {
		if (!ACCESS.check(new UpdatePruefkoerperPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		store(formData.getId(), mapToRecord(formData)); 
		return formData;
	}


	private PruefkoerperFormData mapToFormData(PruefkoerperFormData formData, PruefkoerperRecord pruefkoerper) {
		formData.getNr().setValue(pruefkoerper.getNr().longValue());
		formData.getProdukt().setValue(pruefkoerper.getProduktId());
		formData.getForm().setValue(pruefkoerper.getForm());
		formData.getColor().setValue(pruefkoerper.getColorId());
		formData.getColorDescription().setValue(pruefkoerper.getColorDescription());
		formData.getLaenge().setValue(pruefkoerper.getLaenge().longValue());
		formData.getBreite().setValue(pruefkoerper.getBreite().longValue());
		formData.getDicke().setValue(pruefkoerper.getDicke().longValue());
		formData.getSchank().setValue(pruefkoerper.getSchrank());
		formData.getBoxe().setValue(pruefkoerper.getBoxe());
		formData.getBestand().setValue(pruefkoerper.getBestand().longValue());
		formData.getActive().setValue(pruefkoerper.getActive());

		return formData;
	}

	private PruefkoerperRecord mapToRecord(PruefkoerperFormData formData) {
		Pruefkoerper t = getTable();
		return new PruefkoerperRecord()
				.with(t.ID, formData.getId())
				.with(t.NR, l2i(formData.getNr().getValue()))
				.with(t.PRODUKT_ID, formData.getProdukt().getValue())
				.with(t.FORM, formData.getForm().getValue())
				.with(t.COLOR_ID, formData.getColor().getValue())
				.with(t.COLOR_DESCRIPTION, formData.getColorDescription().getValue())
				.with(t.LAENGE, l2i(formData.getLaenge().getValue()))
				.with(t.BREITE, l2i(formData.getBreite().getValue()))
				.with(t.DICKE, l2i(formData.getDicke().getValue()))
				.with(t.SCHRANK, formData.getSchank().getValue())
				.with(t.BOXE, formData.getBoxe().getValue())
				.with(t.BESTAND, l2i(formData.getBestand().getValue()))
				.with(t.ACTIVE, formData.getActive().getValue());
	}
	
	private Integer l2i(Long value) {
		if(value == null) {
			return null;
		}
		
		return value.intValue();
 	}
}
