package com.github.driftserver.core.metamodel.urn;

import com.github.driftserver.core.metamodel.ModelDescriptor;
import com.github.driftserver.core.metamodel.ModelException;

import java.io.Reader;
import java.io.Writer;

public interface ModelURNResolver {

    public Reader getReader(ModelURN urn, ModelDescriptor modelDescriptor) throws ModelException;

    public Writer getWriter(ModelURN urn, ModelDescriptor modelDescriptor) throws ModelException;

}
