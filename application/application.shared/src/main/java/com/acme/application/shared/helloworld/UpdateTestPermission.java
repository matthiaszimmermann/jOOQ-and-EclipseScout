package com.acme.application.shared.helloworld;

import java.security.BasicPermission;

public class UpdateTestPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public UpdateTestPermission() {
		super(UpdateTestPermission.class.getSimpleName());
	}
}
