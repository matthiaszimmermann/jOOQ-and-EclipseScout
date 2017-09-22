package com.acme.application.group_a.shared.sv;

import java.security.BasicPermission;

public class ReadSicherungsPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public ReadSicherungsPermission() {
		super(ReadSicherungsPermission.class.getSimpleName());
	}
}
