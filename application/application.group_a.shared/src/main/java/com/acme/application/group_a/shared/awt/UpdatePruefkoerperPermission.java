package com.acme.application.group_a.shared.awt;

import java.security.BasicPermission;

public class UpdatePruefkoerperPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public UpdatePruefkoerperPermission() {
		super(UpdatePruefkoerperPermission.class.getSimpleName());
	}
}
