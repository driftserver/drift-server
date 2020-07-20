package io.drift.core.oldstore.serialization;

import io.drift.core.oldstore.ModelStoreException;


public class StorableSerializationException extends ModelStoreException {

	public StorableSerializationException(Exception e) {
		super( "problem with model serialization",e);
	}

}
