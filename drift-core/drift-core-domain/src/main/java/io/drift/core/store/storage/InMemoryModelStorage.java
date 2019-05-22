package io.drift.core.store.storage;

import java.util.HashMap;
import java.util.Map;

public class InMemoryModelStorage extends ModelStorage {

	private Map<StoragePath, String> contentById = new HashMap<StoragePath, String>();

	private Map<StoragePath, String> metaContentById = new HashMap<StoragePath, String>();

	@Override
	public void writeContent(StoragePath storagePath, String content) throws ModelStorageException {
		System.out.println(">>>> " + content);
		contentById.put(storagePath, content);
	}

	@Override
	public String readContent(StoragePath storagePath) throws ModelStorageException {
		return contentById.get(storagePath);
	}

	@Override
	public void writeMetaContent(StoragePath storagePath, String metaContent) throws ModelStorageException {
		System.out.println(">>>> " + metaContent);
		metaContentById.put(storagePath, metaContent);
	}

	@Override
	public String readMetaContent(StoragePath storagePath) throws ModelStorageException {
		return metaContentById.get(storagePath);
	}

}
