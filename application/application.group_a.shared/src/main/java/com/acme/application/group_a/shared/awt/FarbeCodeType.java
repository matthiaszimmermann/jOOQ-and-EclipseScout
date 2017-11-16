package com.acme.application.group_a.shared.awt;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;

import com.acme.application.shared.code.BaseCodeType;
import com.acme.application.shared.code.IApplicationCodeType;

public class FarbeCodeType extends BaseCodeType {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * IMPORTANT: This ID links the code type to the database content
	 */
	public static final String ID = "d7a27e42-4cc2-4afd-a525-82f2ff46447b";

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
		return FarbeCodeType.class;
	}
	
	@Order(10.0)
	public static class Anthracite extends AbstractCode<String> {
		private static final long serialVersionUID = 1L;

		public static final String ID = "A";

		@Override
		public String getId() {
			return ID;
		}
	}	

	@Order(20.0)
	public static class Blau extends AbstractCode<String> {
		private static final long serialVersionUID = 1L;

		public static final String ID = "B";

		@Override
		public String getId() {
			return ID;
		}
	}

	@Order(70.0)
	public static class Schwarz extends AbstractCode<String> {
		private static final long serialVersionUID = 1L;

		public static final String ID = "S";

		@Override
		public String getId() {
			return ID;
		}
	}
}
