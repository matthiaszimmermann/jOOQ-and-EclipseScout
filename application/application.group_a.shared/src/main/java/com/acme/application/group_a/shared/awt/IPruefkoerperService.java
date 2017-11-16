package com.acme.application.group_a.shared.awt;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IPruefkoerperService extends IService {

	PruefkoerperTablePageData getPruefkoerperTableData(SearchFilter filter, boolean activeOnly);

	PruefkoerperFormData prepareCreate(PruefkoerperFormData formData);

	PruefkoerperFormData create(PruefkoerperFormData formData);

	PruefkoerperFormData load(PruefkoerperFormData formData);

	PruefkoerperFormData store(PruefkoerperFormData formData);
}
