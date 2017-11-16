package com.acme.application.group_a.shared.awt;

import java.security.BasicPermission;

public class DeleteProduktPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public DeleteProduktPermission() {
		super(DeleteProduktPermission.class.getSimpleName());
	}
}
