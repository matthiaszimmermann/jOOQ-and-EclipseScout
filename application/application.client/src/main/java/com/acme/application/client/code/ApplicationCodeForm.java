package com.acme.application.client.code;

import java.security.Permission;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;

import com.acme.application.client.code.ApplicationCodeForm.MainBox.CancelButton;
import com.acme.application.client.code.ApplicationCodeForm.MainBox.CodeBox.MainInfoBox;
import com.acme.application.client.code.ApplicationCodeForm.MainBox.CodeBox.MainInfoBox.CodeIdField;
import com.acme.application.client.code.ApplicationCodeForm.MainBox.CodeBox.MainInfoBox.CodeTextField;
import com.acme.application.client.code.ApplicationCodeForm.MainBox.OkButton;
import com.acme.application.client.common.AbstractDirtyFormHandler;
import com.acme.application.client.role.RoleForm.MainBox.RoleBox;
import com.acme.application.client.role.RoleForm.MainBox.RoleBox.PermissionTableField;
import com.acme.application.shared.code.ApplicationCodeFormData;
import com.acme.application.shared.code.ApplicationCodeUtility;
import com.acme.application.shared.code.CreateApplicationCodePermission;
import com.acme.application.shared.code.IApplicationCodeService;
import com.acme.application.shared.code.IApplicationCodeType;
import com.acme.application.shared.code.UpdateApplicationCodePermission;

@FormData(value = ApplicationCodeFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ApplicationCodeForm extends AbstractForm {

	public enum DisplayMode {
		CREATE,
		EDIT;
	}	

	private String codeTypeId;

	@FormData
	public String getCodeTypeId() {
		return codeTypeId;
	}

	@FormData
	public void setCodeTypeId(String codeTypeId) {
		this.codeTypeId = codeTypeId;
	}

	public void setDisplayMode(DisplayMode mode) {
		getCodeTextField().setMandatory(true);
		
		switch (mode) {
		case CREATE:
			getCodeIdField().setEnabled(false);
			getCodeIdField().setMandatory(true);
			break;

		default:
			getCodeIdField().setEnabled(false);
			getCodeIdField().setMandatory(false);
			break;
		}
	}

	@Override
	public Object computeExclusiveKey() {
		String codeId = getCodeIdField().getValue();
		String codeText = TEXTS.getWithFallback(codeId, codeId);
		String typeText = TEXTS.getWithFallback(getCodeTypeId(), getCodeTypeId());

		return String.format("%s %s", typeText, codeText); 
	}

	protected String calculateSubTitle() {
		return (String) computeExclusiveKey();
	}

	@Override
	protected int getConfiguredDisplayHint() {
		return IForm.DISPLAY_HINT_VIEW;
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Code");
	}

	public void startModify() {
		startInternalExclusive(new ModifyHandler());
	}

	public void startNew() {
		startInternal(new NewHandler());
	}

	public CodeIdField getCodeIdField() {
		return getFieldByClass(CodeIdField.class);
	}

	public CodeTextField getCodeTextField() {
		return getFieldByClass(CodeTextField.class);
	}

	public CancelButton getCancelButton() {
		return getFieldByClass(CancelButton.class);
	}

	public MainInfoBox getMySequenceBox() {
		return getFieldByClass(MainInfoBox.class);
	}

	public OkButton getOkButton() {
		return getFieldByClass(OkButton.class);
	}

	public PermissionTableField getPermissionTableField() {
		return getFieldByClass(PermissionTableField.class);
	}

	public RoleBox getRoleBox() {
		return getFieldByClass(RoleBox.class);
	}

	@Order(1000)
	public class MainBox extends AbstractGroupBox {

		@Order(1000)
		public class CodeBox extends AbstractGroupBox {


			@Order(1000)
			public class MainInfoBox extends AbstractSequenceBox {

				@Override
				protected int getConfiguredGridW() {
					return 2;
				}
				
				@Order(1000)
				public class CodeTextField extends AbstractStringField {

					@Override
					protected String getConfiguredLabel() {
						return TEXTS.get("Text");
					}

					@Override
					protected int getConfiguredMaxLength() {
						return 128;
					}
				}

				@Order(2000)
				public class CodeIdField extends AbstractStringField {

					@Override
					protected String getConfiguredLabel() {
						return TEXTS.get("CodeId");
					}

					@Override
					protected boolean getConfiguredMandatory() {
						return true;
					}

					@Override
					protected int getConfiguredMaxLength() {
						return 128;
					}
				}

				@Override
				protected boolean getConfiguredAutoCheckFromTo() {
					return false;
				}
			}

			@Order(3000)
			public class ActiveField extends AbstractBooleanField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Active");
				}
			}
		}

		@Order(100000)
		public class OkButton extends AbstractOkButton {
		}

		@Order(101000)
		public class CancelButton extends AbstractCancelButton {
		}
	}

	public class ModifyHandler extends AbstractDirtyFormHandler {

		@Override
		protected void execLoad() {
//			setEnabledPermission(new UpdateApplicationCodePermission());
//
//			ApplicationCodeFormData formData = new ApplicationCodeFormData();
//			exportFormData(formData);
//			formData = BEANS.get(IApplicationCodeService.class).load(formData);
//			importFormData(formData);
//
//			getForm().setSubTitle(calculateSubTitle());
			load(new UpdateApplicationCodePermission());
		}

		@Override
		protected void execStore() {
			store();
		}

		@Override
		protected void execDirtyStatusChanged(boolean dirty) {
			setSubTitle(calculateSubTitle());
		}

		@Override
		protected boolean getConfiguredOpenExclusive() {
			return true;
		}
	}

	public class NewHandler extends AbstractDirtyFormHandler {

		@Override
		protected void execLoad() {
			setEnabledPermission(new CreateApplicationCodePermission());

			ApplicationCodeFormData formData = new ApplicationCodeFormData();
			exportFormData(formData);
			formData = BEANS.get(IApplicationCodeService.class).load(formData);
			importFormData(formData);

			getForm().setSubTitle(calculateSubTitle());
		}

		@Override
		protected void execStore() {
			store();
		}

		@Override
		protected void execDirtyStatusChanged(boolean dirty) {
			getForm().setSubTitle(calculateSubTitle());
		}
	}

	private void load(Permission permission)  {
		setEnabledPermission(permission);

		ApplicationCodeFormData formData = new ApplicationCodeFormData();
		exportFormData(formData);
		formData = BEANS.get(IApplicationCodeService.class).load(formData);
		importFormData(formData);

		setSubTitle(calculateSubTitle());
	}
	
	private void store()  {
		ApplicationCodeFormData formData = new ApplicationCodeFormData();
		exportFormData(formData);
		formData = BEANS.get(IApplicationCodeService.class).store(formData);
		
		String typeId = formData.getCodeTypeId();
		IApplicationCodeType type = ApplicationCodeUtility.getCodeType(typeId);
		ApplicationCodeUtility.reload(type.getClass());
	}
}
