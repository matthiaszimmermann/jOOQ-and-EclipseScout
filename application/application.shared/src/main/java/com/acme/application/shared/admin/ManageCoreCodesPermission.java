package com.acme.application.shared.admin;

import java.security.BasicPermission;

public class ManageCoreCodesPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public ManageCoreCodesPermission() {
		super(ManageCoreCodesPermission.class.getSimpleName());
	}
}
