package com.acme.application.group_a.client.awt;

import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.form.FormEvent;
import org.eclipse.scout.rt.client.ui.form.FormListener;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import com.acme.application.client.common.AbstractActiveColumn;
import com.acme.application.client.common.AbstractDeleteMenu;
import com.acme.application.client.common.AbstractEditMenu;
import com.acme.application.client.common.AbstractIdColumn;
import com.acme.application.client.common.AbstractNewMenu;
import com.acme.application.group_a.client.awt.PruefkoerperTablePage.Table;
import com.acme.application.group_a.shared.awt.CreatePruefkoerperPermission;
import com.acme.application.group_a.shared.awt.DeletePruefkoerperPermission;
import com.acme.application.group_a.shared.awt.FarbeCodeType;
import com.acme.application.group_a.shared.awt.IPruefkoerperService;
import com.acme.application.group_a.shared.awt.ProduktFamilieCodeType;
import com.acme.application.group_a.shared.awt.PruefkoerperFormData;
import com.acme.application.group_a.shared.awt.PruefkoerperTablePageData;
import com.acme.application.group_a.shared.awt.ReadPruefkoerperPermission;
import com.acme.application.group_a.shared.awt.UpdatePruefkoerperPermission;

@Data(PruefkoerperTablePageData.class)
public class PruefkoerperTablePage extends AbstractPageWithTable<Table> {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Pruefkoerper");
	}

	@Override
	protected void execLoadData(SearchFilter filter) {
		boolean activeOnly = true;
		importPageData(BEANS.get(IPruefkoerperService.class).getPruefkoerperTableData(filter, activeOnly));
	}

	public class Table extends AbstractTable {

		public ActiveColumn getActiveColumn() {
			return getColumnSet().getColumnByClass(ActiveColumn.class);
		}

		public ProduktColumn getProduktColumn() {
			return getColumnSet().getColumnByClass(ProduktColumn.class);
		}

		public ColorColumn getColorColumn() {
			return getColumnSet().getColumnByClass(ColorColumn.class);
		}

		public ColorDescriptionColumn getColorDescriptionColumn() {
			return getColumnSet().getColumnByClass(ColorDescriptionColumn.class);
		}

		public SchrankColumn getSchrankColumn() {
			return getColumnSet().getColumnByClass(SchrankColumn.class);
		}

		public BoxeColumn getBoxeColumn() {
			return getColumnSet().getColumnByClass(BoxeColumn.class);
		}

		public LaengeColumn getLaengeColumn() {
			return getColumnSet().getColumnByClass(LaengeColumn.class);
		}

		public BreiteColumn getBreiteColumn() {
			return getColumnSet().getColumnByClass(BreiteColumn.class);
		}

		public DickeColumn getDickeColumn() {
			return getColumnSet().getColumnByClass(DickeColumn.class);
		}

		public BestandColumn getBestandColumn() {
			return getColumnSet().getColumnByClass(BestandColumn.class);
		}

		public ProduktFamilieColumn getProduktFamilieColumn() {
			return getColumnSet().getColumnByClass(ProduktFamilieColumn.class);
		}

		public FormColumn getFormColumn() {
			return getColumnSet().getColumnByClass(FormColumn.class);
		}

		public BestandMinColumn getBestandMinColumn() {
			return getColumnSet().getColumnByClass(BestandMinColumn.class);
		}

		public NrColumn getNrColumn() {
			return getColumnSet().getColumnByClass(NrColumn.class);
		}

		public IdColumn getIdColumn() {
			return getColumnSet().getColumnByClass(IdColumn.class);
		}


		@Order(10)
		public class NewMenu extends AbstractNewMenu {

			@Override
			protected void execInitAction() {
				setVisibleGranted(ACCESS.check(new CreatePruefkoerperPermission()));
			}
			
			@Override
			protected void execAction() {
				PruefkoerperForm form = new PruefkoerperForm();
				form.addFormListener(new PruefkoerperFormListener());
				form.startNew();
			}
		}

		@Order(20)
		public class EditMenu extends AbstractEditMenu {

			@Override
			protected void execInitAction() {
				setVisibleGranted(ACCESS.check(new ReadPruefkoerperPermission()) || ACCESS.check(new UpdatePruefkoerperPermission()));
			}

			@Override
			protected void execAction() {
				PruefkoerperForm form = new PruefkoerperForm();
				form.addFormListener(new PruefkoerperFormListener());
				form.setId(getSelectedId());
				form.startModify();
			}
		}

		@Order(30)
		public class DeleteMenu extends AbstractDeleteMenu {

			@Override
			protected void execInitAction() {
				setVisibleGranted(ACCESS.check(new DeletePruefkoerperPermission()));
			}

			@Override
			protected void execAction() {
				IPruefkoerperService service = BEANS.get(IPruefkoerperService.class);
				PruefkoerperFormData formData = new PruefkoerperFormData();
				
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

		private class PruefkoerperFormListener implements FormListener {

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
		public class NrColumn extends AbstractIntegerColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Nr");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(3000)
		public class ProduktColumn extends AbstractSmartColumn<String> {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Produkt");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
			
			@Override
			protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
				return ProduktLookupCall.class;
			}
		}

		@Order(3500)
		public class ProduktFamilieColumn extends AbstractSmartColumn<String> {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("ProdFamilie");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
			
			@Override
			protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
				return ProduktFamilieCodeType.class;
			}
		}

		@Order(3750)
		public class FormColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Form");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(4000)
		public class ColorColumn extends AbstractSmartColumn<String> {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Farbe");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
			
			@Override
			protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
				return FarbeCodeType.class;
			}
		}

		@Order(5000)
		public class ColorDescriptionColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("ColorDescription");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(6000)
		public class SchrankColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Schrank");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(7000)
		public class BoxeColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Boxe");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(8000)
		public class LaengeColumn extends AbstractIntegerColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Laenge");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(9000)
		public class BreiteColumn extends AbstractIntegerColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Breite");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(10000)
		public class DickeColumn extends AbstractIntegerColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Dicke");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(11000)
		public class BestandColumn extends AbstractIntegerColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Bestand");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(12000)
		public class BestandMinColumn extends AbstractIntegerColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("BestandMin");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}
		
		@Order(99000)
		public class ActiveColumn extends AbstractActiveColumn {
			
			@Override
			protected boolean getConfiguredDisplayable() {
				return false;
			}
		}
	}
}
