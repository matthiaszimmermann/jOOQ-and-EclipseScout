/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package com.acme.application.group_a.client;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import com.acme.application.group_a.client.awt.PruefkoerperNodePage;
import com.acme.application.group_a.client.sv.SicherungTablePage;
import com.acme.application.group_a.shared.ViewGroupOutlinePermission;
import com.acme.application.shared.FontAwesomeIcons;

public class GroupOutline extends AbstractOutline {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("GroupA");
	}

	@Override
	protected boolean getConfiguredVisible() {
		return ACCESS.check(new ViewGroupOutlinePermission());
	}

	@Override
	protected void execCreateChildPages(List<IPage<?>> pageList) {
		pageList.add(new SicherungTablePage());
		pageList.add(new PruefkoerperNodePage());
	}

	@Override
	protected String getConfiguredIconId() {
		return FontAwesomeIcons.fa_lightbulbO;
	}
}
