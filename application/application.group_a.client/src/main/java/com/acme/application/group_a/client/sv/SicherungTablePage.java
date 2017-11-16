package com.acme.application.group_a.client.sv;

import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.form.FormEvent;
import org.eclipse.scout.rt.client.ui.form.FormListener;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import com.acme.application.client.common.AbstractActiveColumn;
import com.acme.application.client.common.AbstractDeleteMenu;
import com.acme.application.client.common.AbstractEditMenu;
import com.acme.application.client.common.AbstractExportableTable;
import com.acme.application.client.common.AbstractIdColumn;
import com.acme.application.client.common.AbstractNewMenu;
import com.acme.application.group_a.client.sv.SicherungTablePage.Table;
import com.acme.application.group_a.shared.sv.CreateSicherungPermission;
import com.acme.application.group_a.shared.sv.DeleteSicherungPermission;
import com.acme.application.group_a.shared.sv.EtageCodeType;
import com.acme.application.group_a.shared.sv.ISicherungService;
import com.acme.application.group_a.shared.sv.PageSicherungPermission;
import com.acme.application.group_a.shared.sv.ReadSicherungPermission;
import com.acme.application.group_a.shared.sv.SicherungFormData;
import com.acme.application.group_a.shared.sv.SicherungTablePageData;
import com.acme.application.group_a.shared.sv.UpdateSicherungPermission;

@Data(SicherungTablePageData.class)
public class SicherungTablePage extends AbstractPageWithTable<Table> {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("SicherungsVerwaltung");
	}
	
	@Override
	protected boolean getConfiguredLeaf() {
		return true;
	}
	
	@Override
	protected void execInitPage() {
		setVisibleGranted(ACCESS.check(new PageSicherungPermission()));
	}
	
	@Override
	protected void execLoadData(SearchFilter filter) {
		boolean activeOnly = true;
		importPageData(BEANS.get(ISicherungService.class).getSicherungTableData(filter, activeOnly));
	}
	
	public class Table extends AbstractExportableTable {

		@Override
		public IOutline getPageOutline() {
			return getOutline();
		}
		
		@Override
		protected void execRowAction(ITableRow row) {
			getEditMenu().execAction();
		}

		public GebaeudeColumn getGebaeudeColumn() {
			return getColumnSet().getColumnByClass(GebaeudeColumn.class);
		}

		public AnlNrColumn getAnlNrColumn() {
			return getColumnSet().getColumnByClass(AnlNrColumn.class);
		}

		public AnlageColumn getAnlageColumn() {
			return getColumnSet().getColumnByClass(AnlageColumn.class);
		}

		public EtageColumn getEtageColumn() {
			return getColumnSet().getColumnByClass(EtageColumn.class);
		}

		public SicherungColumn getSicherungColumn() {
			return getColumnSet().getColumnByClass(SicherungColumn.class);
		}

		public IfColumn getIfColumn() {
			return getColumnSet().getColumnByClass(IfColumn.class);
		}

		public BefundColumn getBefundColumn() {
			return getColumnSet().getColumnByClass(BefundColumn.class);
		}

		public VisumColumn getVisumColumn() {
			return getColumnSet().getColumnByClass(VisumColumn.class);
		}

		public DatumColumn getDatumColumn() {
			return getColumnSet().getColumnByClass(DatumColumn.class);
		}

		public ActiveColumn getActiveColumn() {
			return getColumnSet().getColumnByClass(ActiveColumn.class);
		}

		public PruefmethodeColumn getPruefmethodeColumn() {
			return getColumnSet().getColumnByClass(PruefmethodeColumn.class);
		}

		public StandortColumn getStandortColumn() {
			return getColumnSet().getColumnByClass(StandortColumn.class);
		}

		public IdColumn getIdColumn() {
			return getColumnSet().getColumnByClass(IdColumn.class);
		}
		
		EditMenu getEditMenu() {
			return getMenuByClass(EditMenu.class);
		}

		@Order(10)
		public class NewMenu extends AbstractNewMenu {

			@Override
			protected void execInitAction() {
				setVisibleGranted(ACCESS.check(new CreateSicherungPermission()));
			}
			
			@Override
			protected void execAction() {
				SicherungForm form = new SicherungForm();
				form.addFormListener(new SicherungFormListener());
				form.startNew();
			}
		}

		@Order(20)
		public class EditMenu extends AbstractEditMenu {

			@Override
			protected void execInitAction() {
				setVisibleGranted(ACCESS.check(new ReadSicherungPermission()) || ACCESS.check(new UpdateSicherungPermission()));
			}

			@Override
			protected void execAction() {
				SicherungForm form = new SicherungForm();
				form.addFormListener(new SicherungFormListener());
				form.setId(getSelectedId());
				form.startModify();
			}
		}

		@Order(30)
		public class DeleteMenu extends AbstractDeleteMenu {

			@Override
			protected void execInitAction() {
				setVisibleGranted(ACCESS.check(new DeleteSicherungPermission()));
			}

			@Override
			protected void execAction() {
				ISicherungService service = BEANS.get(ISicherungService.class);
				SicherungFormData formData = new SicherungFormData();
				
				formData.setId(getSelectedId());
				formData = service.load(formData);
				formData.getActive().setValue(false);
				service.store(formData);
				
				reloadPage();
			}
		}
		
		private String getSelectedId() {
			return getIdColumn().getSelectedValue();
		}

		private class SicherungFormListener implements FormListener {

			@Override
			public void formChanged(FormEvent e) {
				if (FormEvent.TYPE_CLOSED == e.getType() && e.getForm().isFormStored()) {
					reloadPage();
				}
			}
		}

		@Order(1000)
		public class IdColumn extends AbstractIdColumn {
		}

		@Order(2000)
		public class GebaeudeColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("GebNr");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(3000)
		public class AnlNrColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("AnlNr");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(4000)
		public class AnlageColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Anlage");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(5000)
		public class EtageColumn extends AbstractSmartColumn<String> {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Etage");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
			
			@Override
			protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
				return EtageCodeType.class;
			}
		}

		@Order(6000)
		public class StandortColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Standort");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(7000)
		public class SicherungColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Sicherung");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(8000)
		public class IfColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("If");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(9000)
		public class BefundColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Befund");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(10000)
		public class PruefmethodeColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Pruefmethode");
			}
			
			@Override
			protected String getConfiguredHeaderTooltipText() {
				return TEXTS.get("PruefmethodeTooltip");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(11000)
		public class VisumColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Visum");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(12000)
		public class DatumColumn extends AbstractDateColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Datum");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(13000)
		public class ActiveColumn extends AbstractActiveColumn {
			
			@Override
			protected boolean getConfiguredDisplayable() {
				return false;
			}
		}
	}
}
