package com.acme.application.database.generator;

import org.eclipse.scout.rt.testing.platform.runner.PlatformTestRunner;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acme.application.database.table.TableDataInitializer;


@RunWith(PlatformTestRunner.class)
@RunWithSubject("admin")
public class CreateIdTest {


	@Test
	public void createIds()  throws Exception {
		System.out.println(TableDataInitializer.createId());
		System.out.println(TableDataInitializer.createId());
		System.out.println(TableDataInitializer.createId());
	}
}
