package com.acme.application.server.document;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.or.app.tables.Document;
import com.acme.application.database.or.app.tables.records.DocumentRecord;
import com.acme.application.database.table.TableUtility;
import com.acme.application.server.common.BaseService;
import com.acme.application.shared.code.FileCodeType;
import com.acme.application.shared.common.DateTimeUtility;
import com.acme.application.shared.document.DocumentTablePageData;
import com.acme.application.shared.document.DocumentTablePageData.DocumentTableRowData;
import com.acme.application.shared.document.IDocumentService;

public class DocumentService extends BaseService implements IDocumentService {

	private static final Logger LOG = LoggerFactory.getLogger(DocumentService.class);

	@Override
	public BinaryResource getResource(String documentId) {
		DocumentRecord document = get(documentId); 
		return new BinaryResource(document.getName(), document.getContent());
	}

	/**
	 * Returns true iff a document with the specified id exists.
	 */
	public boolean exists(String documentId) {
		Document dt = Document.DOCUMENT;
		DSLContext ctx = getContext();

		return ctx.fetchExists(ctx.select().from(dt).where(dt.ID.eq(documentId)));
	}

	/**
	 * Returns the document object for the specified id. Returns null if no such
	 * object exists.
	 */
	public DocumentRecord get(String documentId) {
		Document dt = Document.DOCUMENT;

		return getContext()
				.selectFrom(dt)
				.where(dt.ID.eq(documentId))
				.fetchOne();
	}

	/**
	 * Returns the document object for the specified id. Returns a new empty
	 * document record if no such object exists.
	 */
	public DocumentRecord getOrCreate(String documentId) {
		DocumentRecord document = get(documentId);

		if (document != null) {
			return document;
		}

		document = new DocumentRecord();
		document.setId(TableUtility.createId());
		document.setActive(true);

		return document;
	}

	/**
	 * Returns all available documents.
	 */
	public List<DocumentRecord> getAll() {
		return getContext()
				.selectFrom(Document.DOCUMENT)
				.fetchStream()
				.collect(Collectors.toList());
	}

	/**
	 * Persists the provided document.
	 */
	public void store(DocumentRecord document) {
		LOG.info("Persist document {}", document);

		if (exists(document.getId())) {
			getContext().executeUpdate(document);
		} else {
			getContext().executeInsert(document);
		}
	}

	@Override
	public DocumentTablePageData getDocumentTableData(SearchFilter filter) {
		DocumentTablePageData pageData = new DocumentTablePageData();

		getAll()
		.stream()
		.forEach(document -> {
			DocumentTableRowData row = pageData.addRow();
			row.setId(document.getId());
			row.setName(document.getName());
			row.setType(document.getType());
			row.setSize(document.getSize().toBigInteger());
			row.setUser(document.getUserId());
			row.setUploaded(DateTimeUtility.convertToDate(document.getUploaded()));
			row.setActive(document.getActive());
		});
		
		return pageData;
	}

	@Override
	public void store(String name, byte[] content, String userId) {
		String id = TableUtility.createId();
		String type = getType(name);
		BigDecimal size = BigDecimal.valueOf(content.length);
		String uploaded = DateTimeUtility.nowInUtcAsString();
		DocumentRecord document = new DocumentRecord(id, name, type, size, content, userId, uploaded, true);
		store(document);
	}
	
	private String getType(String name) {
        String type = IOUtility.getFileExtension(name);
        return BEANS.get(FileCodeType.class).getCode(type) != null ? type : FileCodeType.UknownCode.ID;
	}
}
