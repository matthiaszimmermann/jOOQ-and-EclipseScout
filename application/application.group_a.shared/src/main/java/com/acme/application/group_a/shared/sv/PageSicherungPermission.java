package com.acme.application.group_a.shared.sv;

import java.security.BasicPermission;

public class PageSicherungPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public PageSicherungPermission() {
		super(PageSicherungPermission.class.getSimpleName());
	}
}
