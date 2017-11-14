package com.acme.application.group_a.shared;

import java.security.BasicPermission;

public class ViewGroupOutlinePermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  public ViewGroupOutlinePermission() {
    super(ViewGroupOutlinePermission.class.getSimpleName());
  }
}
