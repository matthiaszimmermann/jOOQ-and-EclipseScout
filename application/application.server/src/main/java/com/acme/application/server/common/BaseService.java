package com.acme.application.server.common;

import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.jooq.DSLContext;

import com.acme.application.database.generator.Config;
import com.acme.application.server.sql.MsSqlService;

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
//			config = BEANS.get(DerbySqlService.class).getConfig();
			config = BEANS.get(MsSqlService.class).getConfig();
		}
		
		return config;
	}
}
