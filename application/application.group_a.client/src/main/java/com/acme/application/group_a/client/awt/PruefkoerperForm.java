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
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import com.acme.application.group_a.client.awt.PruefkoerperForm.MainBox.CancelButton;
import com.acme.application.group_a.client.awt.PruefkoerperForm.MainBox.DetailBox;
import com.acme.application.group_a.client.awt.PruefkoerperForm.MainBox.DetailBox.AblageBox;
import com.acme.application.group_a.client.awt.PruefkoerperForm.MainBox.DetailBox.AblageBox.BestandField;
import com.acme.application.group_a.client.awt.PruefkoerperForm.MainBox.DetailBox.AblageBox.BoxeField;
import com.acme.application.group_a.client.awt.PruefkoerperForm.MainBox.DetailBox.AblageBox.SchankField;
import com.acme.application.group_a.client.awt.PruefkoerperForm.MainBox.OkButton;
import com.acme.application.group_a.client.awt.PruefkoerperForm.MainBox.TopBox;
import com.acme.application.group_a.client.awt.PruefkoerperForm.MainBox.TopBox.ColorDescriptionField;
import com.acme.application.group_a.client.awt.PruefkoerperForm.MainBox.TopBox.ColorField;
import com.acme.application.group_a.client.awt.PruefkoerperForm.MainBox.TopBox.DimensionBox;
import com.acme.application.group_a.client.awt.PruefkoerperForm.MainBox.TopBox.DimensionBox.BreiteField;
import com.acme.application.group_a.client.awt.PruefkoerperForm.MainBox.TopBox.DimensionBox.DickeField;
import com.acme.application.group_a.client.awt.PruefkoerperForm.MainBox.TopBox.NrField;
import com.acme.application.group_a.client.awt.PruefkoerperForm.MainBox.TopBox.ProduktField;
import com.acme.application.group_a.shared.awt.CreatePruefkoerperPermission;
import com.acme.application.group_a.shared.awt.FarbeCodeType;
import com.acme.application.group_a.shared.awt.IPruefkoerperService;
import com.acme.application.group_a.shared.awt.PruefkoerperFormData;
import com.acme.application.group_a.shared.awt.UpdatePruefkoerperPermission;
import com.acme.application.group_a.client.awt.PruefkoerperForm.MainBox.TopBox.FormField;
import com.acme.application.group_a.client.awt.PruefkoerperForm.MainBox.TopBox.ActiveField;

@FormData(value = PruefkoerperFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class PruefkoerperForm extends AbstractForm {

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
		return getNrField().getValue().toString();
	}

	@Override
	protected int getConfiguredDisplayHint() {
		return IForm.DISPLAY_HINT_VIEW;
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Pruefkoerper");
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

	public ProduktField getProduktField() {
		return getFieldByClass(ProduktField.class);
	}

	public ColorField getColorField() {
		return getFieldByClass(ColorField.class);
	}

	public ColorDescriptionField getColorDescriptionField() {
		return getFieldByClass(ColorDescriptionField.class);
	}

	public BreiteField getBreiteField() {
		return getFieldByClass(BreiteField.class);
	}

	public DickeField getDickeField() {
		return getFieldByClass(DickeField.class);
	}

	public AblageBox getAblageBox() {
		return getFieldByClass(AblageBox.class);
	}

	public BestandField getBestandField() {
		return getFieldByClass(BestandField.class);
	}

	public DimensionBox getMySequenceBox() {
		return getFieldByClass(DimensionBox.class);
	}

	public DetailBox getDetailBox() {
		return getFieldByClass(DetailBox.class);
	}

	public SchankField getSchankField() {
		return getFieldByClass(SchankField.class);
	}

	public BoxeField getBoxeField() {
		return getFieldByClass(BoxeField.class);
	}

	public FormField getFormField() {
		return getFieldByClass(FormField.class);
	}

	public ActiveField getActiveField() {
		return getFieldByClass(ActiveField.class);
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

			@Order(1000)
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
					return 999999999999L;
				}
				
				@Override
				protected boolean getConfiguredMandatory() {
					return true;
				}
			}

			@Order(2000)
			public class ProduktField extends AbstractSmartField<String> {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("ProdFamilie");
				}
				
				@Override
				protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
					return ProduktLookupCall.class;
				}
				
				@Override
				protected boolean getConfiguredMandatory() {
					return true;
				}
			}

			@Order(3000)
			public class ColorField extends AbstractSmartField<String> {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Farbe");
				}
				
				@Override
				protected boolean getConfiguredMandatory() {
					return true;
				}
				
				@Override
				protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
					return FarbeCodeType.class;
				}
			}

			@Order(4000)
			public class ColorDescriptionField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("ColorDescription");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 128;
				}
			}

			@Order(4500)
			public class FormField extends AbstractStringField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Form");
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 128;
				}
				
				@Override
				protected boolean getConfiguredMandatory() {
					return true;
				}
			}

			@Order(5000)
			public class DimensionBox extends AbstractSequenceBox {

				@Order(1000)
				public class LaengeField extends AbstractLongField {
					@Override
					protected String getConfiguredLabel() {
						return TEXTS.get("Laenge");
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

				@Order(2000)
				public class BreiteField extends AbstractLongField {
					@Override
					protected String getConfiguredLabel() {
						return TEXTS.get("Breite");
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

				@Order(3000)
				public class DickeField extends AbstractLongField {
					@Override
					protected String getConfiguredLabel() {
						return TEXTS.get("Dicke");
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

				@Override
				protected boolean getConfiguredAutoCheckFromTo() {
					return false;
				}
			}

			@Order(6000)
			public class ActiveField extends AbstractBooleanField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Active");
				}
			}
			
			
		}

		@Order(2000)
		public class DetailBox extends AbstractTabBox {

			@Order(1000)
			public class AblageBox extends AbstractGroupBox {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Ablage");
				}

				@Order(1000)
				public class SchankField extends AbstractStringField {
					@Override
					protected String getConfiguredLabel() {
						return TEXTS.get("Schrank");
					}

					@Override
					protected int getConfiguredMaxLength() {
						return 128;
					}
				}

				@Order(2000)
				public class BoxeField extends AbstractStringField {
					@Override
					protected String getConfiguredLabel() {
						return TEXTS.get("Boxe");
					}

					@Override
					protected int getConfiguredMaxLength() {
						return 128;
					}
				}

				@Order(3000)
				public class BestandField extends AbstractLongField {
					@Override
					protected String getConfiguredLabel() {
						return TEXTS.get("Bestand");
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
			IPruefkoerperService service = BEANS.get(IPruefkoerperService.class);
			PruefkoerperFormData formData = new PruefkoerperFormData();
			exportFormData(formData);
			formData = service.load(formData);
			importFormData(formData);

			setEnabledPermission(new UpdatePruefkoerperPermission());
		}

		@Override
		protected void execStore() {
			IPruefkoerperService service = BEANS.get(IPruefkoerperService.class);
			PruefkoerperFormData formData = new PruefkoerperFormData();
			exportFormData(formData);
			service.store(formData);
		}
	}

	public class NewHandler extends AbstractFormHandler {

		@Override
		protected void execLoad() {
			IPruefkoerperService service = BEANS.get(IPruefkoerperService.class);
			PruefkoerperFormData formData = new PruefkoerperFormData();
			exportFormData(formData);
			formData = service.prepareCreate(formData);
			importFormData(formData);

			setEnabledPermission(new CreatePruefkoerperPermission());
		}

		@Override
		protected void execStore() {
			IPruefkoerperService service = BEANS.get(IPruefkoerperService.class);
			PruefkoerperFormData formData = new PruefkoerperFormData();
			exportFormData(formData);
			service.create(formData);
		}
	}
}
