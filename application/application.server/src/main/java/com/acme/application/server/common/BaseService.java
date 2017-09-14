package com.acme.application.server.common;

import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.jooq.DSLContext;

import com.acme.application.database.generator.Config;
import com.acme.application.server.sql.SqlService;

@ApplicationScoped
public class BaseService {

	private Config config = null;
	private DSLContext context = null;
	
	protected DSLContext getContext() {
		if(context == null) {
			context = getConfig().getContext();
		}
		
		return context;
	}
	
	protected Config getConfig() {
		if(config == null) {
			config = BEANS.get(SqlService.class).getConfig();
		}
		
		return config;
	}
}
