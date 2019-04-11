package io.drift.core.store.metadata;

import io.drift.core.store.MetaModel;
import io.drift.core.store.storage.Storable;

abstract public class MetaModelCreator<STORABLE extends Storable> {

	abstract public MetaModel createFrom(STORABLE model);

}
