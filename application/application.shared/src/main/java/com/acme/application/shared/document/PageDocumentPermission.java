package com.acme.application.shared.document;

import java.security.BasicPermission;

public class PageDocumentPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public PageDocumentPermission() {
		super(PageDocumentPermission.class.getSimpleName());
	}
}
