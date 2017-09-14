package com.acme.application.server.sql;

import javax.annotation.PostConstruct;

import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.CreateImmediately;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.context.RunContext;
import org.eclipse.scout.rt.platform.exception.ExceptionHandler;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.generator.Config;
import com.acme.application.database.generator.GeneratorApplication;
import com.acme.application.database.table.TableDataInitializer;
import com.acme.application.server.code.ApplicationCodeService;
import com.acme.application.server.common.BaseService;
import com.acme.application.server.security.PermissionService;

@ApplicationScoped
@CreateImmediately
public class DatabaseSetupService extends BaseService {
	private static final Logger LOG = LoggerFactory.getLogger(DatabaseSetupService.class);

	@PostConstruct
	public void autoCreateDatabase() {
		if (CONFIG.getPropertyValue(DatabaseProperties.DatabaseAutoCreateProperty.class)) {
			try {
				RunContext context = BEANS.get(SuperUserRunContextProducer.class).produce();
				IRunnable runnable = new IRunnable() {

					@Override
					public void run() throws Exception {
						Config config = BEANS.get(SqlService.class).getConfig();
						createDatabaseScheme(config);
						populateDatabase(config);
						initializeTexts();
						initializeCodes(config);
					}

					private void createDatabaseScheme(Config config) {
						LOG.info("Create database schema");
						GeneratorApplication.setupDatabase(config);
					}

					private void populateDatabase(Config config) {
						LOG.info("Populate database");
						// TODO check why this does not work
						//						SchemaInitializer schema = BEANS.get(SchemaInitializer.class); 
						//						schema.setConfig(config);
						//						schema.initialize();

						TableDataInitializer data = BEANS.get(TableDataInitializer.class);
						data.setConfig(config);
						data.initialize();

						if(CONFIG.getPropertyValue(DatabaseProperties.DatabaseAutoPopulateProperty.class)) {
							LOG.info("Add additional sample data");
							data.addSamples();
						}
					}

					private void initializeCodes(Config config) {
						LOG.info("Initialize codes");
						BEANS.get(ApplicationCodeService.class).setConfig(config);
					}

					// TODO refactor + clean up
//					/**
//					 * Specific method for local codes that adds texts for locale codes using
//					 * method {@link Locale#getDisplayName()}.
//					 */
//					private void reloadLocaleCodes(Config config) {
//						IApplicationCodeType codeType = ApplicationCodeUtility.getCodeType(LocaleCodeType.class); 
//
//						BEANS.get(ApplicationCodeService.class)
//						.getCodeRecords(codeType.getId())
//						.forEach(code -> {
//							String text = Locale.forLanguageTag(code.getId()).getDisplayName();
//							codeType.storeDynamicCode(
//									new CodeRow<String>(code.getId(), text)
//									.withIconId(code.getIcon())
//									.withActive(code.getActive()));
//						});
//					}

					private void initializeTexts() {
						LOG.info("Initialize texts");
						BEANS.get(PermissionService.class).checkTranslations();
					}
				};

				context.run(runnable);
			}
			catch (RuntimeException e) {
				BEANS.get(ExceptionHandler.class).handle(e);
			}
		}
	}

	//	@Override
	public void dropDataStore() {
		LOG.error("Add implementation");
		//		SQL.update(SQLs.PERSON_DROP_TABLE);
		//		SQL.update(SQLs.ORGANIZATION_DROP_TABLE);
	}

	//	@Override
	public void createDataStore() {
		LOG.error("Add implementation");
		//		createOrganizationTable();
		//		createPersonTable();
	}
}
