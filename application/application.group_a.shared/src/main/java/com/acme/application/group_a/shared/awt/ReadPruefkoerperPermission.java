package com.acme.application.group_a.shared.awt;

import java.security.BasicPermission;

public class ReadPruefkoerperPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public ReadPruefkoerperPermission() {
		super(ReadPruefkoerperPermission.class.getSimpleName());
	}
}
