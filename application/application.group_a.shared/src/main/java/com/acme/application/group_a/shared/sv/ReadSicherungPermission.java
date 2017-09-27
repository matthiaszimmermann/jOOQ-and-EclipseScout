package com.acme.application.group_a.shared.sv;

import java.security.BasicPermission;

public class ReadSicherungPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public ReadSicherungPermission() {
		super(ReadSicherungPermission.class.getSimpleName());
	}
}
