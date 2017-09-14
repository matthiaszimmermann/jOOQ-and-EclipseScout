package com.acme.application.server.user;

import java.security.Permission;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.IPermissionService;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.or.app.tables.Role;
import com.acme.application.database.or.app.tables.RolePermission;
import com.acme.application.database.or.app.tables.records.RolePermissionRecord;
import com.acme.application.database.or.app.tables.records.RoleRecord;
import com.acme.application.database.table.RoleTable;
import com.acme.application.server.common.BaseService;
import com.acme.application.server.security.PermissionService;
import com.acme.application.shared.role.IRoleService;
import com.acme.application.shared.role.PermissionTablePageData;
import com.acme.application.shared.role.RoleFormData;
import com.acme.application.shared.role.RoleTablePageData;
import com.acme.application.shared.role.RoleFormData.PermissionTable;
import com.acme.application.shared.role.RoleFormData.PermissionTable.PermissionTableRowData;
import com.acme.application.shared.role.RoleTablePageData.RoleTableRowData;

public class RoleService extends BaseService implements IRoleService {

	private static final Logger LOG = LoggerFactory.getLogger(RoleService.class);


	/**
	 * Returns true iff the role specified by the provided name exists.
	 */
	@Override
	public boolean exists(String role) {
		Role rt = Role.ROLE;
		DSLContext ctx = getContext();

		return ctx.fetchExists(
				ctx.select()
				.from(rt)
				.where(rt.NAME.eq(role)));
	}

	/**
	 * Returns the role object for the role specified by the provided name.
	 */
	public RoleRecord get(String role) {
		Role rt = Role.ROLE;

		return getContext()
				.selectFrom(rt)
				.where(rt.NAME.eq(role))
				.fetchOne();
	}

	/**
	 * Returns the role object for the specified id.
	 * Returns a new empty person record if no such person exists.
	 */
	public RoleRecord getOrCreate(String roleName) {
		RoleRecord role = get(roleName);

		if(role != null) {
			return role;
		}

		role = new RoleRecord();
		role.setName(roleName);

		return role;
	}

	/**
	 * Returns all active available roles.
	 */
	public List<RoleRecord> getAll() {
		return getAll(true);
	}

	/**
	 * Returns all available roles.
	 */
	public List<RoleRecord> getAll(boolean active) {
		return getContext()
				.selectFrom(Role.ROLE)
				.fetchStream()
				.filter(role -> role.getActive() == active)
				.collect(Collectors.toList());
	}

	/**
	 * Returns the set of permissions for the role specified by the provided name.
	 */
	public List<Permission> getPermissions(String role) {
		RolePermission rpt = RolePermission.ROLE_PERMISSION;
		PermissionService permissionService = BEANS.get(PermissionService.class);

		return getContext()
				.select(rpt.PERMISSION)
				.from(rpt)
				.where(rpt.ROLE_NAME.eq(role))
				.fetchStream()
				.map(record -> {
					String permissionId = rpt.field(rpt.PERMISSION).getValue(record);
					return permissionService.getPermission(permissionId);
				})
				.collect(Collectors.toList());
	}

	/**
	 * Persists the provided role, including associated permissions.
	 */
	public void store(RoleRecord role, List<String> permissions) {
		LOG.info("persisting role {}", role);

		store(role);
		storeRolePermissions(role, permissions);
	}

	/**
	 * Persists the provided role.
	 */
	private void store(RoleRecord role) {
		LOG.info("persist role {}", role);

		if(exists(role.getName())) { getContext().executeUpdate(role); }
		else { getContext().executeInsert(role); }
	}	

	/**
	 * Persists the provided role permission.
	 */
	private void storeRolePermissions(RoleRecord role, List<String> permissions) {

		// delete existing role permissions
		String roleName = role.getName();
		RolePermission rpt = RolePermission.ROLE_PERMISSION;
		getContext()
		.deleteFrom(rpt)
		.where(rpt.ROLE_NAME.eq(roleName))
		.execute();

		// add new user roles
		permissions.stream()
		.forEach(permission -> getContext()
				.executeInsert(new RolePermissionRecord(roleName, permission)));
	}

	@Override
	public AbstractTablePageData getRoleTableData(SearchFilter filter) {
		RoleTablePageData pageData = new RoleTablePageData();

		getAll()
		.stream()
		.forEach(role -> {
			String roleName = role.getName();

			RoleTableRowData row = pageData.addRow();
			row.setId(roleName);
			row.setName(TEXTS.getWithFallback(roleName, roleName));
		});

		return pageData;
	}
	

	@Override
	public AbstractTablePageData getPermissionTableData(SearchFilter filter) {
		PermissionTablePageData pageData = new PermissionTablePageData();
		
		BEANS.get(PermissionService.class)
		.getAllPermissionClasses()
		.stream()
		.forEach(permission -> {
			com.acme.application.shared.role.PermissionTablePageData.PermissionTableRowData row = pageData.addRow();
			String id = permission.getName();
			String group = permission.getPackage().getName();
			row.setId(id);
			row.setGroup(TEXTS.getWithFallback(group, group));
			row.setText(TEXTS.getWithFallback(id, id));
		});
		
		return pageData;
	}
	

	@Override
	public RoleFormData load(RoleFormData formData) {
		List<String> permissions = getRolePermissions(formData);
		addPermissionRows(formData, permissions);

		return formData;
	}

	private List<String> getRolePermissions(RoleFormData formData) {
		String role = formData.getRoleId().getValue();

		return getPermissions(role)
				.stream()
				.map(permission -> {
					return permission.getClass().getName();
				})
				.collect(Collectors.toList()); 
	}

	private void addPermissionRows(RoleFormData formData, List<String> permissions) {
		String role = formData.getRoleId().getValue();
		boolean isRoot = RoleTable.ROOT.equals(role);
		
		PermissionTable table = formData.getPermissionTable();
		table.clearRows();

		BEANS.get(IPermissionService.class)
		.getAllPermissionClasses()
		.stream()
		.forEach(permission -> {
			String id = permission.getName();
			String groupId = permission.getPackage().getName();
			String group = TEXTS.getWithFallback(groupId, groupId);
			String name = TEXTS.getWithFallback(id, id);
			PermissionTableRowData row = table.addRow();
			
			row.setId(id);
			row.setGroup(group);
			row.setText(name);
			row.setAssigned(permissions.contains(id) || isRoot);
		});
	}

	@Override
	public RoleFormData store(RoleFormData formData) {
		String role = formData.getRoleId().getValue();
		PermissionTable table = formData.getPermissionTable();
		RoleRecord record = new RoleRecord(role, true);
		List<String> permissions = Arrays.asList(table.getRows())
				.stream()
				.filter(row -> row.getAssigned())
				.map(row -> {
					return row.getId();
				})
				.collect(Collectors.toList());
		
		store(record, permissions);
		
		return formData;
	}
}
