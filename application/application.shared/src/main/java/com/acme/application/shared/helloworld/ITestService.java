package com.acme.application.shared.helloworld;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface ITestService extends IService {

	TestFormData prepareCreate(TestFormData formData);

	TestFormData create(TestFormData formData);

	TestFormData load(TestFormData formData);

	TestFormData store(TestFormData formData);
}
