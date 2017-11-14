package com.acme.application.client.admin;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import com.acme.application.client.text.TextTablePage;
import com.acme.application.shared.FontAwesomeIcons;
import com.acme.application.shared.ViewWorkOutlinePermission;

@Order(3000)
public class AdminOutline extends AbstractOutline {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Administration");
	}

	@Override
	protected String getConfiguredIconId() {
		return FontAwesomeIcons.fa_users;
	}

	@Override
	protected boolean getConfiguredVisible() {
		return ACCESS.check(new ViewWorkOutlinePermission());
	}

	@Override
	protected void execCreateChildPages(List<IPage<?>> pageList) {
		pageList.add(new AccountNodePage());
		pageList.add(new CodeNodePage());
		pageList.add(new TextTablePage());
	}
}
