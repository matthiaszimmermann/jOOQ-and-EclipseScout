package com.acme.application.shared.user;

import java.security.Permission;
import java.util.List;
import java.util.Locale;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;

import com.acme.application.shared.user.ProfileFormData;
import com.acme.application.shared.user.UserFormData;

@TunnelToServer
public interface IUserService extends IService {
	
	/**
	 * Returns true iff the provided user name already exists.
	 */
	boolean exists(String username);
	
	/**
	 * Returns all user names.
	 */
	List<String> getUsernames();

	/**
	 * Returns the permissions for the user with the specified user name. 
	 */
	List<Permission> getPermissions(String username);

	/**
	 * Returns true iff the provided password is valid for the provider user name.
	 */
	boolean verifyPassword(String username, String password);	
	
	UserFormData load(UserFormData formData);

	UserFormData store(UserFormData formData);
	
	/**
	 * Returns users all users as lookup rows.
	 * @param activeOnly: restricts result set to active records iff active is true
	 */
	public List<? extends ILookupRow<String>> getLookupRows(boolean activeOnly);	
	
	AbstractTablePageData getUserTableData(SearchFilter filter);

	ProfileFormData load(ProfileFormData formData);

	ProfileFormData store(ProfileFormData formData);

	Locale getLocale(String userId);
}
