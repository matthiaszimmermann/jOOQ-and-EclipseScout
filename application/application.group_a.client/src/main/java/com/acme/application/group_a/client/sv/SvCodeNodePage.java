package com.acme.application.group_a.client.sv;

import java.util.List;

import org.eclipse.scout.rt.client.extension.ui.desktop.outline.pages.AbstractPageWithNodesExtension;
import org.eclipse.scout.rt.client.extension.ui.desktop.outline.pages.PageWithNodesChains.PageWithNodesCreateChildPagesChain;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import com.acme.application.client.admin.CodeNodePage;
import com.acme.application.client.code.ApplicationCodeTablePage;
import com.acme.application.group_a.shared.sv.EtageCodeType;
import com.acme.application.group_a.shared.sv.ManageSvCodesPermission;

public class SvCodeNodePage extends AbstractPageWithNodesExtension<CodeNodePage> {

	public SvCodeNodePage(CodeNodePage owner) {
		super(owner);
	}
	
	@Override
	public void execCreateChildPages(PageWithNodesCreateChildPagesChain chain, List<IPage<?>> pageList) {
		super.execCreateChildPages(chain, pageList);
		
		if(ACCESS.check(new ManageSvCodesPermission())) { 
			pageList.add(new CodesPages());
		}
	}
	
	class CodesPages extends AbstractPageWithNodes {

		@Override
		protected String getConfiguredTitle() {
			return TEXTS.get("SicherungsVerwaltung");
		}
		
		@Override
		protected void execCreateChildPages(List<IPage<?>> pageList) {
			super.execCreateChildPages(pageList);
			pageList.add(new ApplicationCodeTablePage(EtageCodeType.class));
		}
	}	
}
