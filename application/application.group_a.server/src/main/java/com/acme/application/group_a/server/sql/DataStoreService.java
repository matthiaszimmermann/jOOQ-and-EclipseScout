package com.acme.application.group_a.server.sql;

import org.eclipse.scout.rt.platform.config.CONFIG;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.server.ServerProperties;
import com.acme.application.server.sql.IDataStoreService;

public class DataStoreService implements IDataStoreService {

	private static final Logger LOG = LoggerFactory.getLogger(DataStoreService.class);

	@Override
	public void create() {
		createDatabaseScheme();
		populateDatabase();
	}

	private void populateDatabase() {
		LOG.warn("Add implementation");
		if(CONFIG.getPropertyValue(ServerProperties.DatabaseAutoCreateProperty.class)) {
			// TODO add implementation
		}		
	}

	private void createDatabaseScheme() {
		LOG.warn("Add implementation");
		if(CONFIG.getPropertyValue(ServerProperties.DatabaseAutoPopulateProperty.class)) {
			// TODO Auto-generated method stub
		}		
	}

	@Override
	public void drop() {
		LOG.warn("Add implementation");
		if(CONFIG.getPropertyValue(ServerProperties.DatabaseAutoCreateProperty.class)) {
			// TODO add implementation
		}
	}
}
