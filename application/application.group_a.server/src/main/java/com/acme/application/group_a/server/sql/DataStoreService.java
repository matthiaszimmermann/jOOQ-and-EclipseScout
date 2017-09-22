package com.acme.application.group_a.server.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.server.sql.IDataStoreService;

public class DataStoreService implements IDataStoreService {
	
	private static final Logger LOG = LoggerFactory.getLogger(DataStoreService.class);

	@Override
	public void create() {
		LOG.warn("Add implementation");
	}

	@Override
	public void drop() {
		LOG.warn("Add implementation");
	}
}
