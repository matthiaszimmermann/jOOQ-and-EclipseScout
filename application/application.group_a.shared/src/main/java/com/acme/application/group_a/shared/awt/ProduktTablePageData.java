package com.acme.application.group_a.shared.awt;

import javax.annotation.Generated;

import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications
 * recommended.
 */
@Generated(value = "com.acme.application.group_a.client.awt.ProduktTablePage", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class ProduktTablePageData extends AbstractTablePageData {

	private static final long serialVersionUID = 1L;

	@Override
	public ProduktTableRowData addRow() {
		return (ProduktTableRowData) super.addRow();
	}

	@Override
	public ProduktTableRowData addRow(int rowState) {
		return (ProduktTableRowData) super.addRow(rowState);
	}

	@Override
	public ProduktTableRowData createRow() {
		return new ProduktTableRowData();
	}

	@Override
	public Class<? extends AbstractTableRowData> getRowType() {
		return ProduktTableRowData.class;
	}

	@Override
	public ProduktTableRowData[] getRows() {
		return (ProduktTableRowData[]) super.getRows();
	}

	@Override
	public ProduktTableRowData rowAt(int index) {
		return (ProduktTableRowData) super.rowAt(index);
	}

	public void setRows(ProduktTableRowData[] rows) {
		super.setRows(rows);
	}

	public static class ProduktTableRowData extends AbstractTableRowData {

		private static final long serialVersionUID = 1L;
		public static final String id = "id";
		public static final String nr = "nr";
		public static final String bezeichnung = "bezeichnung";
		public static final String prodFam = "prodFam";
		public static final String bestandMin = "bestandMin";
		public static final String active = "active";
		private String m_id;
		private Integer m_nr;
		private String m_bezeichnung;
		private String m_prodFam;
		private Integer m_bestandMin;
		private Boolean m_active;

		public String getId() {
			return m_id;
		}

		public void setId(String newId) {
			m_id = newId;
		}

		public Integer getNr() {
			return m_nr;
		}

		public void setNr(Integer newNr) {
			m_nr = newNr;
		}

		public String getBezeichnung() {
			return m_bezeichnung;
		}

		public void setBezeichnung(String newBezeichnung) {
			m_bezeichnung = newBezeichnung;
		}

		public String getProdFam() {
			return m_prodFam;
		}

		public void setProdFam(String newProdFam) {
			m_prodFam = newProdFam;
		}

		public Integer getBestandMin() {
			return m_bestandMin;
		}

		public void setBestandMin(Integer newBestandMin) {
			m_bestandMin = newBestandMin;
		}

		public Boolean getActive() {
			return m_active;
		}

		public void setActive(Boolean newActive) {
			m_active = newActive;
		}
	}
}
