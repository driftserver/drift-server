package io.drift.core.store.storage;

import java.util.HashMap;
import java.util.Map;

public class InMemoryModelStorage extends ModelStorage {

	private Map<StorageId, String> contentById = new HashMap<StorageId, String>();

	private Map<StorageId, String> metaContentById = new HashMap<StorageId, String>();

	@Override
	public void writeContent(StorageId storageId, String content) throws ModelStorageException {
		System.out.println(">>>> " + content);
		contentById.put(storageId, content);
	}

	@Override
	public String readContent(StorageId storageId) throws ModelStorageException {
		return contentById.get(storageId);
	}

	@Override
	public void writeMetaContent(StorageId storageId, String metaContent) throws ModelStorageException {
		System.out.println(">>>> " + metaContent);
		metaContentById.put(storageId, metaContent);
	}

	@Override
	public String readMetaContent(StorageId storageId) throws ModelStorageException {
		return metaContentById.get(storageId);
	}

}
