package com.acme.application.client.admin;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import com.acme.application.client.role.PermissionTablePage;
import com.acme.application.client.role.RoleTablePage;
import com.acme.application.client.user.UserTablePage;
import com.acme.application.shared.admin.AccountPagePermission;

public class AccountNodePage extends AbstractPageWithNodes {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("AccountNodePage");
	}

	@Override
	protected boolean execCalculateVisible() {
		return ACCESS.check(new AccountPagePermission());
	}
	
	@Override
	protected void execCreateChildPages(List<IPage<?>> pageList) {
		pageList.add(new UserTablePage());
		pageList.add(new RoleTablePage());
		pageList.add(new PermissionTablePage());
	}
}
