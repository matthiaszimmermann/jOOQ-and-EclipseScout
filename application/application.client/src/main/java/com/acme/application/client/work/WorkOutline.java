package com.acme.application.client.work;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import com.acme.application.client.document.DocumentTablePage;
import com.acme.application.shared.Icons;
import com.acme.application.shared.ViewWorkOutlinePermission;

/**
 * <h3>{@link WorkOutline}</h3>
 */
@Order(1000)
public class WorkOutline extends AbstractOutline {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Work");
	}

	@Override
	protected String getConfiguredIconId() {
		return Icons.Pencil;
	}

	@Override
	protected boolean getConfiguredVisible() {
		return ACCESS.check(new ViewWorkOutlinePermission());
	}

	@Override
	protected void execCreateChildPages(List<IPage<?>> pageList) {
		super.execCreateChildPages(pageList);
		
		pageList.add(new DocumentTablePage());
	}
}
