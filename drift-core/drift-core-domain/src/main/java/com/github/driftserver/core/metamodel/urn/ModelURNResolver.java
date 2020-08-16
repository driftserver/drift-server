package com.github.driftserver.core.metamodel.urn;

import com.github.driftserver.core.metamodel.ModelException;
import com.github.driftserver.core.metamodel.ModelFormat;

import java.io.Reader;
import java.io.Writer;

public interface ModelURNResolver {

    Reader getReader(ModelURN urn, ModelFormat format) throws ModelException;

    Writer getWriter(ModelURN urn, ModelFormat format) throws ModelException;

}
