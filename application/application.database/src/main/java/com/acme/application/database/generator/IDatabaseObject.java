package com.acme.application.database.generator;

import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.slf4j.Logger;

@ApplicationScoped
public interface IDatabaseObject {
	
	Config getConfig();
	void setConfig(Config config);
	
	String getName();
	String getCreateSQL();
	
	void create();
	void drop();
	
	Logger getLogger();
}
