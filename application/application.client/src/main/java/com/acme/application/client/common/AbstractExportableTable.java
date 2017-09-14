package com.acme.application.client.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;

import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.action.IAction;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.desktop.OpenUriAction;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.docx4j.client.ScoutXlsxSpreadsheetAdapter;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractExportableTable extends AbstractTable {
	
	private static final Logger LOG = LoggerFactory.getLogger(AbstractExportableTable.class);
	
	// TODO check if there is a better way to do this (ie. extending classes not having to implement getPageOutline...
	abstract public IOutline getPageOutline();
	
	@Order(100000)
	public class ExportToExcelMenu extends AbstractMenu {

		@Override
		protected String getConfiguredIconId() {
			return FontAwesomeIcons.fa_shareSquareO;
		}
		
		@Override
		protected String getConfiguredTooltipText() {
			return TEXTS.get("ExportToExcel");
		}
		
		@Override
		protected boolean getConfiguredVisible() {
			return getPageOutline() != null;
		}
		
		@Override
		protected byte getConfiguredHorizontalAlignment() {
			return IAction.HORIZONTAL_ALIGNMENT_RIGHT;
		}

		@Override
		protected Set<? extends IMenuType> getConfiguredMenuTypes() {
			return CollectionUtility.hashSet(TableMenuType.EmptySpace, TableMenuType.SingleSelection, TableMenuType.MultiSelection);
		}

		@Override
		protected void execAction() {
			if (pageIsActive()) { 
				File xlsx = createExcelFile();
				downloadFile(xlsx);					
			} 
		}

		private boolean pageIsActive() {
			return getPageOutline() != null && getPageOutline().getActivePage() != null;
		}

		private File createExcelFile() {
			ScoutXlsxSpreadsheetAdapter s = new ScoutXlsxSpreadsheetAdapter(); 
			File xlsx = s.exportPage(null, 0, 0, getPageOutline().getActivePage());
			return xlsx;
		}

		private void downloadFile(File file) {
			try {
				byte [] fileContent = Files.readAllBytes(file.toPath());
				BinaryResource fileAsResource = new BinaryResource(file.getName(), fileContent);
				ClientSessionProvider.currentSession()
				.getDesktop()
				.openUri(fileAsResource, OpenUriAction.DOWNLOAD);
			} 
			catch (IOException e) {
				LOG.error("Failed to read file content from " + file.getAbsolutePath(), e);
			}
		}
	}
}
