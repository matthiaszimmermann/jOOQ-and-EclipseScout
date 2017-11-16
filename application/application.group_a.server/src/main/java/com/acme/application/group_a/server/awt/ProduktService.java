package com.acme.application.group_a.server.awt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.jooq.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.or.awt.tables.Produkt;
import com.acme.application.database.or.awt.tables.records.ProduktRecord;
import com.acme.application.group_a.shared.awt.CreateProduktPermission;
import com.acme.application.group_a.shared.awt.IProduktService;
import com.acme.application.group_a.shared.awt.ProduktFormData;
import com.acme.application.group_a.shared.awt.ProduktTablePageData;
import com.acme.application.group_a.shared.awt.ProduktTablePageData.ProduktTableRowData;
import com.acme.application.group_a.shared.awt.ReadProduktPermission;
import com.acme.application.group_a.shared.awt.UpdateProduktPermission;
import com.acme.application.server.common.AbstractBaseService;

public class ProduktService extends AbstractBaseService<Produkt, ProduktRecord> implements IProduktService {

	@Override
	public Produkt getTable() {
		return Produkt.PRODUKT;
	}

	@Override
	public Field<String> getIdColumn() {
		return Produkt.PRODUKT.ID;
	}

	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger(ProduktService.class);
	}

	@Override
	public ProduktTablePageData getProduktTableData(SearchFilter filter) {
		ProduktTablePageData pageData = new ProduktTablePageData();

		getAll().stream().forEach(produkt -> {
			addToPageData(pageData, produkt);
		});

		return pageData;
	}

	private void addToPageData(ProduktTablePageData pageData, ProduktRecord produkt) {
		ProduktTableRowData row = pageData.addRow();
		// TODO cleanup two columns for same attribute
		row.setId(produkt.getId());
		row.setNr(Integer.valueOf(produkt.getId()));
		row.setProdFam(produkt.getFamilie());
		row.setBezeichnung(produkt.getBezeichnung());
		row.setBestandMin(produkt.getBestandMin());
		row.setActive(produkt.getActive());
	}

	@Override
	public List<? extends ILookupRow<String>> getLookupRows(boolean activeOnly) {
		List<ILookupRow<String>> list = new ArrayList<>();

		getAll().stream().filter(product -> !activeOnly || (activeOnly && product.getActive())).forEach(product -> {
			list.add(new LookupRow<>(product.getId(), getProductDisplayName(product)));
		});

		return list;
	}

	private String getProductDisplayName(ProduktRecord product) {
		String displayName = product.getBezeichnung() + " (" + product.getId() + ")";
		return displayName;
	}

	@Override
	public ProduktFormData prepareCreate(ProduktFormData formData) {
		if (!ACCESS.check(new CreateProduktPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}
		
		formData.getActive().setValue(true);
		return formData;
	}

	@Override
	public ProduktFormData create(ProduktFormData formData) {
		if (!ACCESS.check(new CreateProduktPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}
		
		store(formData.getId(), mapToRecord(formData)); 
		return formData;
	}

	@Override
	public ProduktFormData load(ProduktFormData formData) {
		if (!ACCESS.check(new ReadProduktPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		ProduktRecord produkt = get(formData.getId());
		return mapToFormData(formData, produkt);
	}

	@Override
	public ProduktFormData store(ProduktFormData formData) {
		if (!ACCESS.check(new UpdateProduktPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}
		
		store(formData.getId(), mapToRecord(formData)); 
		return formData;
	}

	private ProduktFormData mapToFormData(ProduktFormData formData, ProduktRecord produkt) {
		formData.getNr().setValue(Long.parseLong(produkt.getId()));
		formData.getBezeichnung().setValue(produkt.getBezeichnung());
		formData.getProdFamilie().setValue(produkt.getFamilie());
		formData.getProdArt().setValue(produkt.getArt());
		formData.getBestandMin().setValue(produkt.getBestandMin().longValue());
		formData.getActive().setValue(produkt.getActive());

		return formData;
	}

	private ProduktRecord mapToRecord(ProduktFormData formData) {
		Produkt t = getTable();
		return new ProduktRecord()
				.with(t.ID, formData.getId())
				.with(t.BEZEICHNUNG, formData.getBezeichnung().getValue())
				.with(t.FAMILIE, formData.getProdFamilie().getValue())
				.with(t.ART, formData.getProdArt().getValue())
				.with(t.BESTAND_MIN, formData.getBestandMin().getValue().intValue())
				.with(t.ACTIVE, formData.getActive().getValue());
	}
}
