package io.drift.core.metamodel.serialization;

import io.drift.core.metamodel.Model;
import io.drift.core.metamodel.ModelDescriptor;
import io.drift.core.metamodel.ModelException;
import io.drift.core.metamodel.ModelFormat;

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
