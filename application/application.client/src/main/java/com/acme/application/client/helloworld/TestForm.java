package com.acme.application.client.helloworld;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;

import com.acme.application.client.helloworld.TestForm.MainBox.CancelButton;
import com.acme.application.client.helloworld.TestForm.MainBox.OkButton;
import com.acme.application.shared.helloworld.CreateTestPermission;
import com.acme.application.shared.helloworld.ITestService;
import com.acme.application.shared.helloworld.TestFormData;
import com.acme.application.shared.helloworld.UpdateTestPermission;

@FormData(value = TestFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class TestForm extends AbstractForm {

	@Override
	protected String getConfiguredTitle() {
		// TODO [mzi] verify translation
		return TEXTS.get("Test");
	}

	public void startModify() {
		startInternalExclusive(new ModifyHandler());
	}

	public void startNew() {
		startInternal(new NewHandler());
	}

	public CancelButton getCancelButton() {
		return getFieldByClass(CancelButton.class);
	}

	public MainBox getMainBox() {
		return getFieldByClass(MainBox.class);
	}

	public OkButton getOkButton() {
		return getFieldByClass(OkButton.class);
	}

	@Order(1000)
	public class MainBox extends AbstractGroupBox {

		@Order(100000)
		public class OkButton extends AbstractOkButton {
		}

		@Order(101000)
		public class CancelButton extends AbstractCancelButton {
		}
	}

	public class ModifyHandler extends AbstractFormHandler {

		@Override
		protected void execLoad() {
			ITestService service = BEANS.get(ITestService.class);
			TestFormData formData = new TestFormData();
			exportFormData(formData);
			formData = service.load(formData);
			importFormData(formData);

			setEnabledPermission(new UpdateTestPermission());
		}

		@Override
		protected void execStore() {
			ITestService service = BEANS.get(ITestService.class);
			TestFormData formData = new TestFormData();
			exportFormData(formData);
			service.store(formData);
		}
	}

	public class NewHandler extends AbstractFormHandler {

		@Override
		protected void execLoad() {
			ITestService service = BEANS.get(ITestService.class);
			TestFormData formData = new TestFormData();
			exportFormData(formData);
			formData = service.prepareCreate(formData);
			importFormData(formData);

			setEnabledPermission(new CreateTestPermission());
		}

		@Override
		protected void execStore() {
			ITestService service = BEANS.get(ITestService.class);
			TestFormData formData = new TestFormData();
			exportFormData(formData);
			service.create(formData);
		}
	}
}
