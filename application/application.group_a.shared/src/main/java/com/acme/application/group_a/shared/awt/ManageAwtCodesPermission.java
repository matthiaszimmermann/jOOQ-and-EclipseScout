package com.acme.application.group_a.shared.awt;

import java.security.BasicPermission;

public class ManageAwtCodesPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public ManageAwtCodesPermission() {
		super(ManageAwtCodesPermission.class.getSimpleName());
	}
}
