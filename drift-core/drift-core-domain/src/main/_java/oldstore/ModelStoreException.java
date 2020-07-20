package io.drift.core.oldstore;


public class ModelStoreException extends Exception {

	public ModelStoreException(String msg, Exception e) {
		super( "model store exception: " + msg,e);
	}

}
