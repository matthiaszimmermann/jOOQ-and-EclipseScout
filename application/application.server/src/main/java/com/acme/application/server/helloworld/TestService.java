package com.acme.application.server.helloworld;

import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import com.acme.application.shared.helloworld.CreateTestPermission;
import com.acme.application.shared.helloworld.ITestService;
import com.acme.application.shared.helloworld.ReadTestPermission;
import com.acme.application.shared.helloworld.TestFormData;
import com.acme.application.shared.helloworld.UpdateTestPermission;

public class TestService implements ITestService {

	@Override
	public TestFormData prepareCreate(TestFormData formData) {
		if (!ACCESS.check(new CreateTestPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}
		// TODO [mzi] add business logic here.
		return formData;
	}

	@Override
	public TestFormData create(TestFormData formData) {
		if (!ACCESS.check(new CreateTestPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}
		// TODO [mzi] add business logic here.
		return formData;
	}

	@Override
	public TestFormData load(TestFormData formData) {
		if (!ACCESS.check(new ReadTestPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}
		// TODO [mzi] add business logic here.
		return formData;
	}

	@Override
	public TestFormData store(TestFormData formData) {
		if (!ACCESS.check(new UpdateTestPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}
		// TODO [mzi] add business logic here.
		return formData;
	}
}
