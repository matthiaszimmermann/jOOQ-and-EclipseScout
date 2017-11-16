package com.acme.application.group_a.shared.awt;

import java.security.BasicPermission;

public class DeletePruefkoerperPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public DeletePruefkoerperPermission() {
		super(DeletePruefkoerperPermission.class.getSimpleName());
	}
}
