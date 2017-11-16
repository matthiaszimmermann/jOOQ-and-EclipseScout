package com.acme.application.group_a.client.awt;

import java.util.List;

import org.eclipse.scout.rt.client.extension.ui.desktop.outline.pages.AbstractPageWithNodesExtension;
import org.eclipse.scout.rt.client.extension.ui.desktop.outline.pages.PageWithNodesChains.PageWithNodesCreateChildPagesChain;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import com.acme.application.client.admin.CodeNodePage;
import com.acme.application.client.code.ApplicationCodeTablePage;
import com.acme.application.group_a.shared.awt.FarbeCodeType;
import com.acme.application.group_a.shared.awt.ManageAwtCodesPermission;
import com.acme.application.group_a.shared.awt.ProduktFamilieCodeType;

public class AwtCodeNodePage extends AbstractPageWithNodesExtension<CodeNodePage> {

	public AwtCodeNodePage(CodeNodePage owner) {
		super(owner);
	}
	
	@Override
	public void execCreateChildPages(PageWithNodesCreateChildPagesChain chain, List<IPage<?>> pageList) {
		super.execCreateChildPages(chain, pageList);
		
		if(ACCESS.check(new ManageAwtCodesPermission())) {
			pageList.add(new CodesPages());
		}
	}
	
	class CodesPages extends AbstractPageWithNodes {

		@Override
		protected String getConfiguredTitle() {
			return TEXTS.get("Awt");
		}
		
		@Override
		protected void execCreateChildPages(List<IPage<?>> pageList) {
			super.execCreateChildPages(pageList);
			pageList.add(new ApplicationCodeTablePage(FarbeCodeType.class));
			pageList.add(new ApplicationCodeTablePage(ProduktFamilieCodeType.class));
		}
	}	
}
