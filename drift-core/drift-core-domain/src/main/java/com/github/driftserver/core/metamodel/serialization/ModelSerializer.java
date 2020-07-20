package com.github.driftserver.core.metamodel.serialization;

import com.github.driftserver.core.metamodel.Model;
import com.github.driftserver.core.metamodel.ModelDescriptor;
import com.github.driftserver.core.metamodel.ModelFormat;
import com.github.driftserver.core.metamodel.ModelException;

import java.io.Reader;
import java.io.Writer;

abstract public class ModelSerializer {

	private ModelFormat format;

	ModelSerializer(ModelFormat format) {
		super();
		this.format = format;
	}

	public ModelFormat getFormat() {
		return format;
	}

	abstract public void write(Writer writer, Model model, ModelDescriptor descriptor) throws ModelException;

	abstract public Model read(Reader reader, ModelDescriptor descriptor) throws ModelException;

}
