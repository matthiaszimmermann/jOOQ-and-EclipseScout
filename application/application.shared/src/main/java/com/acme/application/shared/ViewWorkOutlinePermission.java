package com.acme.application.shared;

import java.security.BasicPermission;

public class ViewWorkOutlinePermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  public ViewWorkOutlinePermission() {
    super(ViewWorkOutlinePermission.class.getSimpleName());
  }
}
