package com.acme.application.group_a.shared.sv;

import java.security.BasicPermission;

public class UpdateSicherungsPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public UpdateSicherungsPermission() {
		super(UpdateSicherungsPermission.class.getSimpleName());
	}
}
