package com.acme.application.shared.admin;

import java.security.BasicPermission;

public class AccountPagePermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  public AccountPagePermission() {
    super(AccountPagePermission.class.getSimpleName());
  }
}
