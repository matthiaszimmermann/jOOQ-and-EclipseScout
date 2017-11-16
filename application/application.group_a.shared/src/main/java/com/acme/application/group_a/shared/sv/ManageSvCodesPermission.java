package com.acme.application.group_a.shared.sv;

import java.security.BasicPermission;

public class ManageSvCodesPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public ManageSvCodesPermission() {
		super(ManageSvCodesPermission.class.getSimpleName());
	}
}
