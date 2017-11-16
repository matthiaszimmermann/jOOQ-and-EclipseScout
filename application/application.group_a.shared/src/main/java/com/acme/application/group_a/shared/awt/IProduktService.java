package com.acme.application.group_a.shared.awt;

import java.util.List;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;

@TunnelToServer
public interface IProduktService extends IService {

	ProduktTablePageData getProduktTableData(SearchFilter filter);

	/**
	 * Returns all products as lookup rows.
	 * 
	 * @param activeOnly:
	 *            restricts result set to active records iff active is true
	 */
	List<? extends ILookupRow<String>> getLookupRows(boolean activeOnly);

	ProduktFormData prepareCreate(ProduktFormData formData);

	ProduktFormData create(ProduktFormData formData);

	ProduktFormData load(ProduktFormData formData);

	ProduktFormData store(ProduktFormData formData);
}
