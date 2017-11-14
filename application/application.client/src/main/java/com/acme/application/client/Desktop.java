package com.acme.application.client;

import java.util.List;

import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktop;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import com.acme.application.client.admin.AdminOutline;
import com.acme.application.client.user.ProfileForm;
import com.acme.application.client.work.WorkOutline;
import com.acme.application.shared.FontAwesomeIcons;
import com.acme.application.shared.Icons;
import com.acme.application.shared.ViewAdminOutlinePermission;

/**
 * <h3>{@link Desktop}</h3>
 *
 * @author mzi
 */
public class Desktop extends AbstractDesktop {
	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("ApplicationTitle");
	}

	@Override
	protected String getConfiguredLogoId() {
		return Icons.AppLogo;
	}

	@Override
	protected List<Class<? extends IOutline>> getConfiguredOutlines() {
		return CollectionUtility.<Class<? extends IOutline>>arrayList(
				WorkOutline.class, 
				// TODO reenable search outline once it is needed
				// SearchOutline.class,
				AdminOutline.class);
	}

	@Override
	protected void execDefaultView() {
		selectFirstVisibleOutline();
	}

	protected void selectFirstVisibleOutline() {
		for (IOutline outline : getAvailableOutlines()) {
			if (outline.isEnabled() && outline.isVisible()) {
				setOutline(outline.getClass());
				return;
			}
		}
	}

	@Order(1000)
	public class HelpMenu extends AbstractMenu {

		@Override
		protected String getConfiguredIconId() {
			return FontAwesomeIcons.fa_infoCircle;
		}

		@Override
		protected String getConfiguredKeyStroke() {
			return IKeyStroke.F9;
		}

		@Override
		protected String getConfiguredTooltipText() {
			return TEXTS.get("Info");
		}

		@Override
		protected void execAction() {
			ScoutInfoForm form = new ScoutInfoForm();
			form.startModify();
		}
	}

	@Order(2000)
	public class UserMenu extends AbstractMenu {

		@Override
		protected String getConfiguredText() {
			return ClientSession.get().getUserId();
		}
		
		@Override
		protected String getConfiguredIconId() {
			return FontAwesomeIcons.fa_user;
		}

		@Override
		protected String getConfiguredKeyStroke() {
			return IKeyStroke.F10;
		}

		@Override
		protected String getConfiguredTooltipText() {
			return TEXTS.get("User");
		}

		@Order(1000)
		public class ProfileMenu extends AbstractMenu {

			@Override
			protected String getConfiguredIconId() {
				return FontAwesomeIcons.fa_cog;
			}
			
			@Override
			protected String getConfiguredText() {
				return TEXTS.get("Profile");
			}

			@Override
			protected void execAction() {
				ProfileForm form = new ProfileForm();
				form.startModify();
			}
		}

		@Order(2000)
		public class LogoutMenu extends AbstractMenu {

			@Override
			protected String getConfiguredIconId() {
				return FontAwesomeIcons.fa_signOut;
			}
			
			@Override
			protected String getConfiguredText() {
				return TEXTS.get("Logout");
			}

			@Override
			protected void execAction() {
				ClientSessionProvider.currentSession(ClientSession.class).stop();
			}
		}
	}

	@Order(1000)
	public class WorkOutlineViewButton extends AbstractOutlineViewButton {

		public WorkOutlineViewButton() {
			this(WorkOutline.class);
		}

		protected WorkOutlineViewButton(Class<? extends WorkOutline> outlineClass) {
			super(Desktop.this, outlineClass);
		}

		@Override
		protected String getConfiguredKeyStroke() {
			return IKeyStroke.F2;
		}
	}

	// TODO reenable once search outline is needed
	/*
	@Order(2000)
	public class SearchOutlineViewButton extends AbstractOutlineViewButton {

		public SearchOutlineViewButton() {
			this(SearchOutline.class);
		}

		protected SearchOutlineViewButton(Class<? extends SearchOutline> outlineClass) {
			super(Desktop.this, outlineClass);
		}

		@Override
		protected DisplayStyle getConfiguredDisplayStyle() {
			return DisplayStyle.TAB;
		}

		@Override
		protected String getConfiguredKeyStroke() {
			return IKeyStroke.F3;
		}
	}
	*/

	@Order(3000)
	public class AdminOutlineViewButton extends AbstractOutlineViewButton {

		public AdminOutlineViewButton() {
			this(AdminOutline.class);
		}

		protected AdminOutlineViewButton(Class<? extends AdminOutline> outlineClass) {
			super(Desktop.this, outlineClass);
		}

		@Override
		protected void execInitAction() {
			setVisibleGranted(ACCESS.check(new ViewAdminOutlinePermission()));
		}

		@Override
		protected String getConfiguredIconId() {
			return FontAwesomeIcons.fa_users;
		}

		@Override
		protected DisplayStyle getConfiguredDisplayStyle() {
			return DisplayStyle.TAB;
		}

		@Override
		protected String getConfiguredKeyStroke() {
			return IKeyStroke.F3;
		}
	}
}
