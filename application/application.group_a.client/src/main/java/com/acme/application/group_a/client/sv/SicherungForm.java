package com.acme.application.group_a.client.sv;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;

import com.acme.application.client.common.AbstractDirtyFormHandler;
import com.acme.application.group_a.client.sv.SicherungForm.MainBox.CancelButton;
import com.acme.application.group_a.client.sv.SicherungForm.MainBox.OkButton;
import com.acme.application.group_a.client.sv.SicherungForm.MainBox.TobBox;
import com.acme.application.group_a.client.sv.SicherungForm.MainBox.TobBox.ActiveField;
import com.acme.application.group_a.client.sv.SicherungForm.MainBox.TobBox.AnlNrField;
import com.acme.application.group_a.client.sv.SicherungForm.MainBox.TobBox.AnlageField;
import com.acme.application.group_a.client.sv.SicherungForm.MainBox.TobBox.BefundField;
import com.acme.application.group_a.client.sv.SicherungForm.MainBox.TobBox.DatumField;
import com.acme.application.group_a.client.sv.SicherungForm.MainBox.TobBox.EtageField;
import com.acme.application.group_a.client.sv.SicherungForm.MainBox.TobBox.GebNrField;
import com.acme.application.group_a.client.sv.SicherungForm.MainBox.TobBox.IfField;
import com.acme.application.group_a.client.sv.SicherungForm.MainBox.TobBox.PruefmethodeField;
import com.acme.application.group_a.client.sv.SicherungForm.MainBox.TobBox.SicherungField;
import com.acme.application.group_a.client.sv.SicherungForm.MainBox.TobBox.StandortField;
import com.acme.application.group_a.client.sv.SicherungForm.MainBox.TobBox.VisumField;
import com.acme.application.group_a.shared.sv.CreateSicherungsPermission;
import com.acme.application.group_a.shared.sv.EtageCodeType;
import com.acme.application.group_a.shared.sv.ISicherungService;
import com.acme.application.group_a.shared.sv.SicherungFormData;
import com.acme.application.group_a.shared.sv.UpdateSicherungsPermission;

@FormData(value = SicherungFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class SicherungForm extends AbstractForm {

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
		String gebnr = getGebNrField().getValue();
		String sicherung = getSicherungtField().getValue();
		String if_ = getIfField().getValue();
		return StringUtility.concatenateTokens(gebnr, " ", sicherung, " ", if_);
	}

	@Override
	protected int getConfiguredDisplayHint() {
		return IForm.DISPLAY_HINT_VIEW;
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Sicherung");
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

	public GebNrField getGebNrField() {
		return getFieldByClass(GebNrField.class);
	}

	public AnlNrField getAnlNrField() {
		return getFieldByClass(AnlNrField.class);
	}

	public AnlageField getAnlageField() {
		return getFieldByClass(AnlageField.class);
	}

	public EtageField getEtageField() {
		return getFieldByClass(EtageField.class);
	}

	public StandortField getStandortField() {
		return getFieldByClass(StandortField.class);
	}

	public SicherungField getSicherungtField() {
		return getFieldByClass(SicherungField.class);
	}

	public IfField getIfField() {
		return getFieldByClass(IfField.class);
	}

	public BefundField getBefundField() {
		return getFieldByClass(BefundField.class);
	}

	public PruefmethodeField getPruefmethodeField() {
		return getFieldByClass(PruefmethodeField.class);
	}

	public VisumField getVisumField() {
		return getFieldByClass(VisumField.class);
	}

	public DatumField getDatumField() {
		return getFieldByClass(DatumField.class);
	}

	public ActiveField getActiveField() {
		return getFieldByClass(ActiveField.class);
	}

	public TobBox getTobBox() {
		return getFieldByClass(TobBox.class);
	}

	public OkButton getOkButton() {
		return getFieldByClass(OkButton.class);
	}

	@Order(1000)
	public class MainBox extends AbstractGroupBox {


		@Order(0)
		public class TobBox extends AbstractGroupBox {

			@Order(1000)
			public class GebNrField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("GebNr");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 128;
				}
			}

			@Order(2000)
			public class AnlNrField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("AnlNr");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 128;
				}
			}

			@Order(3000)
			public class AnlageField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Anlage");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 128;
				}
			}

			@Order(4000)
			public class EtageField extends AbstractSmartField<String> {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Etage");
				}

				@Override
				protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
					return EtageCodeType.class;
				}
			}

			@Order(5000)
			public class StandortField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Standort");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 128;
				}
			}

			@Order(6000)
			public class SicherungField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Sicherung");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 128;
				}
			}

			@Order(7000)
			public class IfField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("If");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 128;
				}
			}

			@Order(8000)
			public class BefundField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Befund");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 128;
				}
			}

			@Order(9000)
			public class PruefmethodeField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Pruefmethode");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 128;
				}
			}

			@Order(10000)
			public class VisumField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Visum");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 128;
				}
			}

			@Order(11000)
			public class DatumField extends AbstractDateField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Datum");
				}
			}

			@Order(12000)
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
			ISicherungService service = BEANS.get(ISicherungService.class);
			SicherungFormData formData = new SicherungFormData();
			exportFormData(formData);
			formData = service.load(formData);
			importFormData(formData);

			setEnabledPermission(new UpdateSicherungsPermission());
			getForm().setSubTitle(calculateSubTitle());			
		}

		@Override
		protected void execStore() {
			ISicherungService service = BEANS.get(ISicherungService.class);
			SicherungFormData formData = new SicherungFormData();
			exportFormData(formData);
			service.store(formData);
		}

		@Override
		protected void execDirtyStatusChanged(boolean dirty) {
			getForm().setSubTitle(calculateSubTitle());
		}
	}

	public class NewHandler extends AbstractDirtyFormHandler {

		@Override
		protected void execLoad() {
			ISicherungService service = BEANS.get(ISicherungService.class);
			SicherungFormData formData = new SicherungFormData();
			exportFormData(formData);
			formData = service.prepareCreate(formData);
			importFormData(formData);

			setEnabledPermission(new CreateSicherungsPermission());
			getForm().setSubTitle(calculateSubTitle());			
		}

		@Override
		protected void execStore() {
			ISicherungService service = BEANS.get(ISicherungService.class);
			SicherungFormData formData = new SicherungFormData();
			exportFormData(formData);
			service.create(formData);
		}

		@Override
		protected void execDirtyStatusChanged(boolean dirty) {
			getForm().setSubTitle(calculateSubTitle());
		}
	}
}
