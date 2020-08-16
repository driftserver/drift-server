package com.github.driftserver.core.metamodel;


public class ModelException extends Exception {

	public ModelException(String msg) {
		super(msg);
	}

	public ModelException(String msg, Exception e) {
		super(msg,e);
	}

}
