package com.acme.application.group_a.shared.sv;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface ISicherungService extends IService {

	SicherungFormData prepareCreate(SicherungFormData formData);

	SicherungFormData create(SicherungFormData formData);

	SicherungFormData load(SicherungFormData formData);

	SicherungFormData store(SicherungFormData formData);

	SicherungTablePageData getSicherungTableData(SearchFilter filter);
}
