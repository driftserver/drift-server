package com.github.driftserver.core.metamodel.urn;

import com.github.driftserver.core.metamodel.MetaData;
import com.github.driftserver.core.metamodel.Model;
import com.github.driftserver.core.metamodel.ModelException;
import com.github.driftserver.core.metamodel.ModelFormat;

import java.io.Reader;
import java.io.Writer;
import java.util.List;

public interface ModelURNResolver {

    Reader getReader(ModelURN urn, ModelFormat format, Class<? extends Model> modelClass) throws ModelException;

    Writer getWriter(ModelURN urn, ModelFormat format, Class<? extends Model> modelClass) throws ModelException;

    List<MetaData> listMetaData(ModelURN parentUrn) throws ModelException;
}
