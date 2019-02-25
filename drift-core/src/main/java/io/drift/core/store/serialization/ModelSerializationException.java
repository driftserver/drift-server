package io.drift.core.store.serialization;

import io.drift.core.store.ModelStoreException;


public class ModelSerializationException extends ModelStoreException {

	public ModelSerializationException(Exception e) {
		super( "problem with model serialization",e);
	}

}
