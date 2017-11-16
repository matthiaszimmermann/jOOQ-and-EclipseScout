package com.acme.application.group_a.client.awt;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import com.acme.application.group_a.shared.awt.PagePruefkoerperPermission;

public class PruefkoerperNodePage extends AbstractPageWithNodes {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Awt");
	}
	
	@Override
	protected void execInitPage() {
		setVisibleGranted(ACCESS.check(new PagePruefkoerperPermission()));
	}

	@Override
	protected void execCreateChildPages(List<IPage<?>> pageList) {
		super.execCreateChildPages(pageList);
		pageList.add(new PruefkoerperTablePage());
		pageList.add(new ProduktTablePage());
	}
}
