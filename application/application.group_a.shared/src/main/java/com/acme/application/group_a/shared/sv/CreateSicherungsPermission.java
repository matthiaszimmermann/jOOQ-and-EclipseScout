package com.acme.application.group_a.shared.sv;

import java.security.BasicPermission;

public class CreateSicherungsPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public CreateSicherungsPermission() {
		super(CreateSicherungsPermission.class.getSimpleName());
	}
}
