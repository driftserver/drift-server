package io.drift.core.store.storage;

import io.drift.core.store.ModelStoreException;


public class ModelStorageException extends ModelStoreException {

	public ModelStorageException(Exception e) {
		super( "problem with model storage",e);
	}

}
