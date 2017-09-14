package com.acme.application.client.role;

import java.util.Set;

import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.form.FormEvent;
import org.eclipse.scout.rt.client.ui.form.FormListener;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import com.acme.application.client.common.AbstractExportableTable;
import com.acme.application.client.common.FontAwesomeIcons;
import com.acme.application.client.role.RoleTablePage.Table;
import com.acme.application.client.text.TextForm;
import com.acme.application.shared.role.CreateRolePermission;
import com.acme.application.shared.role.IRoleService;
import com.acme.application.shared.role.ReadRolePagePermission;
import com.acme.application.shared.role.RoleTablePageData;

@Data(RoleTablePageData.class)
public class RoleTablePage extends AbstractPageWithTable<Table> {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("RoleTablePage");
	}

	@Override
	protected boolean getConfiguredLeaf() {
		return true;
	}

	@Override
	protected void execInitPage() {
		setVisiblePermission(new ReadRolePagePermission());
	}

	@Override
	protected void execLoadData(SearchFilter filter) {
		importPageData(BEANS.get(IRoleService.class).getRoleTableData(filter));
	}

	public class Table extends AbstractExportableTable {
		
		@Override
		public IOutline getPageOutline() {
			return getOutline();
		}

		@Override
		protected void execRowAction(ITableRow row) {
			getMenuByClass(EditMenu.class).execAction();
		}

		public NameColumn getNameColumn() {
			return getColumnSet().getColumnByClass(NameColumn.class);
		}

		public IdColumn getIdColumn() {
			return getColumnSet().getColumnByClass(IdColumn.class);
		}

		@Order(10)
		public class NewMenu extends AbstractMenu {

			@Override
			protected void execInitAction() {
				setVisiblePermission(new CreateRolePermission());
			}

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("New");
			}

			@Override
			protected String getConfiguredIconId() {
				return FontAwesomeIcons.fa_magic;
			}

			@Override
			protected String getConfiguredKeyStroke() {
				return "alt-n";
			}

			@Override
			protected Set<? extends IMenuType> getConfiguredMenuTypes() {
				return CollectionUtility.hashSet(TableMenuType.EmptySpace, TableMenuType.SingleSelection, TableMenuType.MultiSelection);
			}

			@Override
			protected void execAction() {
				RoleForm form = new RoleForm();
				form.addFormListener(new RoleFormListener());
				form.startNew();
			}
		}

		@Order(20)
		public class EditMenu extends AbstractMenu {

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("Edit");
			}

			@Override
			protected String getConfiguredIconId() {
				return FontAwesomeIcons.fa_pencil;
			}

			@Override
			protected String getConfiguredKeyStroke() {
				return "alt-e";
			}

			@Override
			protected Set<? extends IMenuType> getConfiguredMenuTypes() {
				return CollectionUtility.hashSet(TableMenuType.SingleSelection);
			}

			@Override
			protected void execAction() {
				String roleId = getIdColumn().getSelectedValue();

				RoleForm form = new RoleForm();
				form.addFormListener(new RoleFormListener());
				form.setRoleId(roleId);
				form.setRoleIdEnabled(false);
				form.startModify();
			}
		}

		@Order(30)
		public class TranslateMenu extends AbstractMenu {
			
			@Override
			protected String getConfiguredText() {
				return TEXTS.get("Translate");
			}

			@Override
			protected String getConfiguredIconId() {
				return FontAwesomeIcons.fa_language;
			}

			@Override
			protected String getConfiguredKeyStroke() {
				return "alt-t";
			}

			@Override
			protected Set<? extends IMenuType> getConfiguredMenuTypes() {
				return CollectionUtility.hashSet(TableMenuType.SingleSelection);
			}

			@Override
			protected void execAction() {
				String roleId = getIdColumn().getSelectedValue();

				TextForm form = BEANS.get(TextForm.class);
				form.setKey(roleId);
				form.startModify();
				form.waitFor();

				if (form.isFormStored()) {
					reloadPage();
				}
			}
		}

		private class RoleFormListener implements FormListener {

			@Override
			public void formChanged(FormEvent e) {
				if (FormEvent.TYPE_CLOSED == e.getType() && e.getForm().isFormStored()) {
					reloadPage();
				}
			}
		}

		@Order(10)
		public class IdColumn extends AbstractStringColumn {

			@Override
			protected boolean getConfiguredPrimaryKey() {
				return true;
			}

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("RoleName");
			}

			@Override
			protected int getConfiguredWidth() {
				return 150;
			}
		}

		@Order(20)
		public class NameColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("RoleText");
			}

			@Override
			protected int getConfiguredWidth() {
				return 200;
			}
		}
	}
}
