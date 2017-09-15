package com.acme.application.database.generator;

import org.eclipse.scout.rt.platform.ApplicationScoped;

@ApplicationScoped
public interface IDataInitializer {

	Config getConfig();
	void setConfig(Config config);
	
	void initialize();
	void addSamples();
}
