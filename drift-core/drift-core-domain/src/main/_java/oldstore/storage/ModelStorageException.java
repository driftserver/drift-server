package io.drift.core.oldstore.storage;

import io.drift.core.oldstore.ModelStoreException;


public class ModelStorageException extends ModelStoreException {

	public ModelStorageException(Exception e) {
		super( "problem with model storage",e);
	}

}
