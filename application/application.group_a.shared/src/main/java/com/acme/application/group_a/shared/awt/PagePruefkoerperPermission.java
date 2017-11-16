package com.acme.application.group_a.shared.awt;

import java.security.BasicPermission;

public class PagePruefkoerperPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public PagePruefkoerperPermission() {
		super(PagePruefkoerperPermission.class.getSimpleName());
	}
}
