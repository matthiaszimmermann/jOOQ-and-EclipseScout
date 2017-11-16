package com.acme.application.group_a.shared.awt;

import java.security.BasicPermission;

public class ReadProduktPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public ReadProduktPermission() {
		super(ReadProduktPermission.class.getSimpleName());
	}
}
