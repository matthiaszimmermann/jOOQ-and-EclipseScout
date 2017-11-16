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

import com.acme.application.client.common.AbstractActiveColumn;
import com.acme.application.client.common.AbstractDeleteMenu;
import com.acme.application.client.common.AbstractEditMenu;
import com.acme.application.client.common.AbstractIdColumn;
import com.acme.application.client.common.AbstractNewMenu;
import com.acme.application.group_a.client.awt.ProduktTablePage.Table;
import com.acme.application.group_a.shared.awt.CreateProduktPermission;
import com.acme.application.group_a.shared.awt.DeleteProduktPermission;
import com.acme.application.group_a.shared.awt.IProduktService;
import com.acme.application.group_a.shared.awt.ProduktFamilieCodeType;
import com.acme.application.group_a.shared.awt.ProduktFormData;
import com.acme.application.group_a.shared.awt.ProduktTablePageData;
import com.acme.application.group_a.shared.awt.ReadProduktPermission;
import com.acme.application.group_a.shared.awt.UpdateProduktPermission;

@Data(ProduktTablePageData.class)
public class ProduktTablePage extends AbstractPageWithTable<Table> {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Produkte");
	}

	@Override
	protected void execLoadData(SearchFilter filter) {
		importPageData(BEANS.get(IProduktService.class).getProduktTableData(filter));
	}

	public class Table extends AbstractTable {

		public BezeichnungColumn getBezeichnungColumn() {
			return getColumnSet().getColumnByClass(BezeichnungColumn.class);
		}

		public ProdFamColumn getProdFamColumn() {
			return getColumnSet().getColumnByClass(ProdFamColumn.class);
		}

		public BestandMinColumn getBestandMinColumn() {
			return getColumnSet().getColumnByClass(BestandMinColumn.class);
		}

		public ActiveColumn getActiveColumn() {
			return getColumnSet().getColumnByClass(ActiveColumn.class);
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
				setVisibleGranted(ACCESS.check(new CreateProduktPermission()));
			}
			
			@Override
			protected void execAction() {
				ProduktForm form = new ProduktForm();
				form.addFormListener(new ProduktFormListener());
				form.startNew();
			}
		}

		@Order(20)
		public class EditMenu extends AbstractEditMenu {

			@Override
			protected void execInitAction() {
				setVisibleGranted(ACCESS.check(new ReadProduktPermission()) || ACCESS.check(new UpdateProduktPermission()));
			}

			@Override
			protected void execAction() {
				ProduktForm form = new ProduktForm();
				form.addFormListener(new ProduktFormListener());
				form.setId(getSelectedId());
				form.startModify();
			}
		}

		@Order(30)
		public class DeleteMenu extends AbstractDeleteMenu {

			@Override
			protected void execInitAction() {
				setVisibleGranted(ACCESS.check(new DeleteProduktPermission()));
			}

			@Override
			protected void execAction() {
				IProduktService service = BEANS.get(IProduktService.class);
				ProduktFormData formData = new ProduktFormData();
				
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

		private class ProduktFormListener implements FormListener {

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
				return TEXTS.get("ProdNr");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(3000)
		public class BezeichnungColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Bezeichnung");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(4000)
		public class ProdFamColumn extends AbstractSmartColumn<String> {
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

		@Order(5000)
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

		@Order(6000)
		public class ActiveColumn extends AbstractActiveColumn {
		}
	}
}
