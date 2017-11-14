package com.acme.application.group_a.shared.sv;

import java.security.BasicPermission;

public class DeleteSicherungPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public DeleteSicherungPermission() {
		super(DeleteSicherungPermission.class.getSimpleName());
	}
}
