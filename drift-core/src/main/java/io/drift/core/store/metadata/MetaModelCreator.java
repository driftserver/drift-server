package io.drift.core.store.metadata;

import io.drift.core.api.Model;
import io.drift.core.store.MetaModel;

abstract public class MetaModelCreator<M extends Model> {

	abstract public MetaModel createFrom(M model);

}
