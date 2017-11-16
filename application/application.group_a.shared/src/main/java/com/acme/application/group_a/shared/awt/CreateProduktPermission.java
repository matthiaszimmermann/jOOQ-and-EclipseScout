package com.acme.application.group_a.shared.awt;

import java.security.BasicPermission;

public class CreateProduktPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public CreateProduktPermission() {
		super(CreateProduktPermission.class.getSimpleName());
	}
}
