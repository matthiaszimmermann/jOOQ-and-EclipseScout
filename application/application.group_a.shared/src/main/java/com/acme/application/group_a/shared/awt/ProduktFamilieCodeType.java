package com.acme.application.group_a.shared.awt;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;

import com.acme.application.shared.code.BaseCodeType;
import com.acme.application.shared.code.IApplicationCodeType;

public class ProduktFamilieCodeType extends BaseCodeType {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * IMPORTANT: This ID links the code type to the database content
	 */
	public static final String ID = "75a32afa-c383-4063-8a38-8ac7e0fef817";

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
		return ProduktFamilieCodeType.class;
	}
	
	@Order(10.0)
	public static class Roh extends AbstractCode<String> {
		private static final long serialVersionUID = 1L;

		public static final String ID = "23c85fde-abdb-4afb-aef3-65136a72a36d";

		@Override
		public String getId() {
			return ID;
		}
	}	

	@Order(110.0)
	public static class H4 extends AbstractCode<String> {
		private static final long serialVersionUID = 1L;

		public static final String ID = "6c880acc-9dc8-434f-85a7-375eaf47c5f4";

		@Override
		public String getId() {
			return ID;
		}
	}
}
