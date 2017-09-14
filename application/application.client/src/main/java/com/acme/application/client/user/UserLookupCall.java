package com.acme.application.client.user;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

import com.acme.application.shared.user.IUserService;

public class UserLookupCall extends LocalLookupCall<String> {

  private static final long serialVersionUID = 1L;

  @Override
  protected List<? extends ILookupRow<String>> execCreateLookupRows() {
    List<ILookupRow<String>> list = new ArrayList<>();

    BEANS.get(IUserService.class)
    	.getUsernames()
    	.forEach(username -> list.add(new LookupRow<>(username, username)));

    return list;
  }
}
