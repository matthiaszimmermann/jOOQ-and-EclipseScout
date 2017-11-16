package com.acme.application.group_a.shared.awt;

import java.security.BasicPermission;

public class UpdateProduktPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public UpdateProduktPermission() {
		super(UpdateProduktPermission.class.getSimpleName());
	}
}
