package com.acme.application.group_a.shared.sv;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;

import com.acme.application.shared.code.BaseCodeType;
import com.acme.application.shared.code.IApplicationCodeType;

public class EtageCodeType extends BaseCodeType {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * IMPORTANT: This ID links the code type to the database content
	 */
	public static final String ID = "4847e87d-ea31-47a3-bb52-42d6a4a50028";

	@Override
	public String getId() {
		return ID;
	}
	
	@Override
	public boolean isDynamic() {
		return true;
	}
	
	@Override
	public Class<? extends IApplicationCodeType> getCodeTypeClass() {
		return EtageCodeType.class;
	}
	
	@Order(10.0)
	public static class UG extends AbstractCode<String> {
		private static final long serialVersionUID = 1L;

		public static final String ID = "UG";

		@Override
		public String getId() {
			return ID;
		}
	}	

	@Order(20.0)
	public static class EG extends AbstractCode<String> {
		private static final long serialVersionUID = 1L;

		public static final String ID = "EG";

		@Override
		public String getId() {
			return ID;
		}
	}

	@Order(30.0)
	public static class OG01 extends AbstractCode<String> {
		private static final long serialVersionUID = 1L;

		public static final String ID = "1.OG";

		@Override
		public String getId() {
			return ID;
		}
	}
}
