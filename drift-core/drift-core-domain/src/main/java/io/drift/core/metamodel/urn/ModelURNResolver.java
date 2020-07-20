package io.drift.core.metamodel.urn;

import io.drift.core.metamodel.ModelDescriptor;
import io.drift.core.metamodel.ModelException;

import java.io.Reader;
import java.io.Writer;

public interface ModelURNResolver {

    public Reader getReader(ModelURN urn, ModelDescriptor modelDescriptor) throws ModelException;

    public Writer getWriter(ModelURN urn, ModelDescriptor modelDescriptor) throws ModelException;

}
