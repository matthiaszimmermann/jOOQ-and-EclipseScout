package com.acme.application.group_a.client.awt;

import java.util.List;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;

import com.acme.application.group_a.shared.awt.IProduktService;

public class ProduktLookupCall extends LocalLookupCall<String> {

	private static final long serialVersionUID = 1L;

	@Override
	protected List<? extends ILookupRow<String>> execCreateLookupRows() {
		return BEANS.get(IProduktService.class).getLookupRows(true);
	}
}
