package com.acme.application.group_a.shared.sv;

import java.security.BasicPermission;

public class CreateSicherungPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public CreateSicherungPermission() {
		super(CreateSicherungPermission.class.getSimpleName());
	}
}
