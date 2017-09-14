package com.acme.application.client.helloworld;

import org.eclipse.scout.rt.client.testenvironment.TestEnvironmentClientSession;
import org.eclipse.scout.rt.testing.client.runner.ClientTestRunner;
import org.eclipse.scout.rt.testing.client.runner.RunWithClientSession;
import org.eclipse.scout.rt.testing.platform.mock.BeanMock;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.acme.application.shared.helloworld.ITestService;
import com.acme.application.shared.helloworld.TestFormData;

@RunWithSubject("anonymous")
@RunWith(ClientTestRunner.class)
@RunWithClientSession(TestEnvironmentClientSession.class)
public class TestFormTest {

	@BeanMock
	private ITestService m_mockSvc;

	@Before
	public void setup() {
		TestFormData answer = new TestFormData();
		Mockito.when(m_mockSvc.prepareCreate(ArgumentMatchers.any())).thenReturn(answer);
		Mockito.when(m_mockSvc.create(ArgumentMatchers.any())).thenReturn(answer);
		Mockito.when(m_mockSvc.load(ArgumentMatchers.any())).thenReturn(answer);
		Mockito.when(m_mockSvc.store(ArgumentMatchers.any())).thenReturn(answer);
	}

	// TODO [mzi] add test cases
}
