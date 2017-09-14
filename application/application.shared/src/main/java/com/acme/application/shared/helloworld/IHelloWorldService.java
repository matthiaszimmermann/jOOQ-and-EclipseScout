package com.acme.application.shared.helloworld;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import com.acme.application.shared.helloworld.HelloWorldFormData;

/**
 * <h3>{@link IHelloWorldService}</h3>
 *
 * @author mzi
 */
@TunnelToServer
public interface IHelloWorldService extends IService {
	HelloWorldFormData load(HelloWorldFormData input);
}
