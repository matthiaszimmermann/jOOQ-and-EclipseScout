package com.acme.application.group_a.shared.sv;

import java.security.BasicPermission;

public class UpdateSicherungPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public UpdateSicherungPermission() {
		super(UpdateSicherungPermission.class.getSimpleName());
	}
}
