package com.acme.application.group_a.shared.awt;

import java.security.BasicPermission;

public class CreatePruefkoerperPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public CreatePruefkoerperPermission() {
		super(CreatePruefkoerperPermission.class.getSimpleName());
	}
}
