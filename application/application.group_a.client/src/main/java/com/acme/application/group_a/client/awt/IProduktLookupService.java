package com.acme.application.group_a.client.awt;

import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

@TunnelToServer
public interface IProduktLookupService extends ILookupService<String> {
}
