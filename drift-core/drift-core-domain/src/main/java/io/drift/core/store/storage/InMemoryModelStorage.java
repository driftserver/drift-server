package io.drift.core.store.storage;

import java.util.HashMap;
import java.util.Map;

public class InMemoryModelStorage extends ModelStorage {

	private Map<StoragePath, String> contentById = new HashMap<StoragePath, String>();

	private Map<StoragePath, String> metaContentById = new HashMap<StoragePath, String>();

	@Override
	public void writeContent(StoragePath storageId, String content) throws ModelStorageException {
		System.out.println(">>>> " + content);
		contentById.put(storageId, content);
	}

	@Override
	public String readContent(StoragePath storageId) throws ModelStorageException {
		return contentById.get(storageId);
	}

	@Override
	public void writeMetaContent(StoragePath storageId, String metaContent) throws ModelStorageException {
		System.out.println(">>>> " + metaContent);
		metaContentById.put(storageId, metaContent);
	}

	@Override
	public String readMetaContent(StoragePath storageId) throws ModelStorageException {
		return metaContentById.get(storageId);
	}

}
