package com.acme.application.client.admin;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import com.acme.application.client.code.ApplicationCodeTablePage;
import com.acme.application.shared.admin.ManageCoreCodesPermission;
import com.acme.application.shared.code.FileCodeType;
import com.acme.application.shared.code.LocaleCodeType;
import com.acme.application.shared.code.SexCodeType;

public class CodeNodePage extends AbstractPageWithNodes {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("CodeNodePage");
	}

	@Override
	protected void execCreateChildPages(List<IPage<?>> pageList) {
		if(ACCESS.check(new ManageCoreCodesPermission())) {
			pageList.add(new CodesPages());
		}
	}

	class CodesPages extends AbstractPageWithNodes {

		@Override
		protected String getConfiguredTitle() {
			return TEXTS.get("Core");
		}

		@Override
		protected void execCreateChildPages(List<IPage<?>> pageList) {
			pageList.add(new ApplicationCodeTablePage(LocaleCodeType.class));
			pageList.add(new ApplicationCodeTablePage(FileCodeType.class));
			pageList.add(new ApplicationCodeTablePage(SexCodeType.class));
		}
	}	
}
