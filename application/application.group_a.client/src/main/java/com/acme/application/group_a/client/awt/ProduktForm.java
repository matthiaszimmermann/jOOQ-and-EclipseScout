package com.acme.application.group_a.client.awt;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;

import com.acme.application.group_a.client.awt.ProduktForm.MainBox.CancelButton;
import com.acme.application.group_a.client.awt.ProduktForm.MainBox.OkButton;
import com.acme.application.group_a.client.awt.ProduktForm.MainBox.TopBox;
import com.acme.application.group_a.client.awt.ProduktForm.MainBox.TopBox.ActiveField;
import com.acme.application.group_a.client.awt.ProduktForm.MainBox.TopBox.BestandMinField;
import com.acme.application.group_a.client.awt.ProduktForm.MainBox.TopBox.BezeichnungField;
import com.acme.application.group_a.client.awt.ProduktForm.MainBox.TopBox.NrField;
import com.acme.application.group_a.client.awt.ProduktForm.MainBox.TopBox.ProdArtField;
import com.acme.application.group_a.client.awt.ProduktForm.MainBox.TopBox.ProdFamilieField;
import com.acme.application.group_a.shared.awt.CreateProduktPermission;
import com.acme.application.group_a.shared.awt.IProduktService;
import com.acme.application.group_a.shared.awt.ProduktFamilieCodeType;
import com.acme.application.group_a.shared.awt.ProduktFormData;
import com.acme.application.group_a.shared.awt.UpdateProduktPermission;

@FormData(value = ProduktFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ProduktForm extends AbstractForm {

	private String id;

	@FormData
	public void setId(String id) {
		this.id = id;
	}

	@FormData
	public String getId() {
		return id;
	}

	@Override
	public Object computeExclusiveKey() {
		return getId();
	}

	protected String calculateSubTitle() {
		return getBezeichnungField().getValue() + " (" + getNrField().getValue() + ")";
	}

	@Override
	protected int getConfiguredDisplayHint() {
		return IForm.DISPLAY_HINT_VIEW;
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Produkt");
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

	public TopBox getTopBox() {
		return getFieldByClass(TopBox.class);
	}

	public ActiveField getActiveField() {
		return getFieldByClass(ActiveField.class);
	}

	public BezeichnungField getBezeichnungField() {
		return getFieldByClass(BezeichnungField.class);
	}

	public ProdFamilieField getProdFamilieField() {
		return getFieldByClass(ProdFamilieField.class);
	}

	public ProdArtField getProdArtField() {
		return getFieldByClass(ProdArtField.class);
	}

	public BestandMinField getBestandMinField() {
		return getFieldByClass(BestandMinField.class);
	}

	public NrField getNrField() {
		return getFieldByClass(NrField.class);
	}

	public OkButton getOkButton() {
		return getFieldByClass(OkButton.class);
	}

	@Order(1000)
	public class MainBox extends AbstractGroupBox {

		
		@Order(1000)
		public class TopBox extends AbstractGroupBox {

			
			@Order(0)
			public class NrField extends AbstractLongField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Nr");
				}

				@Override
				protected Long getConfiguredMinValue() {
					return 0L;
				}

				@Override
				protected Long getConfiguredMaxValue() {
					return 100000000L;
				}
			}

			@Order(500)
			public class BezeichnungField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Bezeichnung");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 128;
				}
			}

			@Order(5250)
			public class ProdFamilieField extends AbstractSmartField<String> {
				
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("ProdFamilie");
				}
				
				@Override
				protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
					return ProduktFamilieCodeType.class;
				}
			}

			@Order(7625)
			public class ProdArtField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("ProdArt");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 128;
				}
			}

			@Order(8812)
			public class BestandMinField extends AbstractLongField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("BestandMin");
				}

				@Override
				protected Long getConfiguredMinValue() {
					return 0L;
				}

				@Override
				protected Long getConfiguredMaxValue() {
					return 999999999999L;
				}
			}

			@Order(10000)
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

	public class ModifyHandler extends AbstractFormHandler {

		@Override
		protected void execLoad() {
			IProduktService service = BEANS.get(IProduktService.class);
			ProduktFormData formData = new ProduktFormData();
			exportFormData(formData);
			formData = service.load(formData);
			importFormData(formData);

			setEnabledPermission(new UpdateProduktPermission());
		}

		@Override
		protected void execStore() {
			IProduktService service = BEANS.get(IProduktService.class);
			ProduktFormData formData = new ProduktFormData();
			exportFormData(formData);
			service.store(formData);
		}
	}

	public class NewHandler extends AbstractFormHandler {

		@Override
		protected void execLoad() {
			IProduktService service = BEANS.get(IProduktService.class);
			ProduktFormData formData = new ProduktFormData();
			exportFormData(formData);
			formData = service.prepareCreate(formData);
			importFormData(formData);

			setEnabledPermission(new CreateProduktPermission());
		}

		@Override
		protected void execStore() {
			IProduktService service = BEANS.get(IProduktService.class);
			ProduktFormData formData = new ProduktFormData();
			exportFormData(formData);
			service.create(formData);
		}
	}
}
