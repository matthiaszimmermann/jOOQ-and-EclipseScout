/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package com.acme.application.group_a.client;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.IPlatform.State;
import org.eclipse.scout.rt.platform.IPlatformListener;
import org.eclipse.scout.rt.platform.PlatformEvent;
import org.eclipse.scout.rt.shared.extension.IExtensionRegistry;

import com.acme.application.group_a.client.awt.AwtCodeNodePage;
import com.acme.application.group_a.client.sv.SvCodeNodePage;

public class PlatformListener implements IPlatformListener {

  @Override
  public void stateChanged(PlatformEvent event) {
    if (event.getState() == State.BeanManagerValid) {
      registerExtensions();
    }
  }

  private void registerExtensions() {
	IExtensionRegistry extensionRegistry = BEANS.get(IExtensionRegistry.class);

    // Register UI extensions
    extensionRegistry.register(SvCodeNodePage.class);
    extensionRegistry.register(AwtCodeNodePage.class);
    
    // Register DTO extensions
    // extensionRegistry.register(PersonTablePageDataExtension.class);
  }
}
