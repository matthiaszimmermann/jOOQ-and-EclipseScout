package com.acme.application.group_a.client.sv;

import java.util.List;

import org.eclipse.scout.rt.client.extension.ui.desktop.outline.pages.AbstractPageWithNodesExtension;
import org.eclipse.scout.rt.client.extension.ui.desktop.outline.pages.PageWithNodesChains.PageWithNodesCreateChildPagesChain;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;

import com.acme.application.client.admin.CodeNodePage;
import com.acme.application.client.code.ApplicationCodeTablePage;
import com.acme.application.group_a.shared.sv.EtageCodeType;

public class CodeNodePageExtension extends AbstractPageWithNodesExtension<CodeNodePage> {

	public CodeNodePageExtension(CodeNodePage owner) {
		super(owner);
	}
	
	@Override
	public void execCreateChildPages(PageWithNodesCreateChildPagesChain chain, List<IPage<?>> pageList) {
		super.execCreateChildPages(chain, pageList);
		pageList.add(new ApplicationCodeTablePage(EtageCodeType.class));
	}
}
