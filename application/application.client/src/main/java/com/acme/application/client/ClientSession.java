package com.acme.application.client;

import org.eclipse.scout.rt.client.AbstractClientSession;
import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.services.common.ping.IPingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.shared.code.ApplicationCodeUtility;
import com.acme.application.shared.user.IUserService;

/**
 * <h3>{@link ClientSession}</h3>
 */
public class ClientSession extends AbstractClientSession {
	private static final Logger LOG = LoggerFactory.getLogger(ClientSession.class);

	public ClientSession() {
		super(true);
	}

	/**
	 * @return The {@link IClientSession} which is associated with the current
	 *         thread, or {@code null} if not found.
	 */
	public static ClientSession get() {
		return ClientSessionProvider.currentSession(ClientSession.class);
	}

	@Override
	protected void execLoadSession() {
		LOG.info("Created a new session for {}", getUserId());
		
		setUserLocale();
		initializeCodeCache();
		setDesktop(new Desktop());
	}

	private void setUserLocale() {
		// forces sync of shared variables for client and server session
		BEANS.get(IPingService.class).ping("");
		setLocale(BEANS.get(IUserService.class).getLocale(getUserId()));
	}

	private void initializeCodeCache() {
		ApplicationCodeUtility.reloadAll();
	}
}
