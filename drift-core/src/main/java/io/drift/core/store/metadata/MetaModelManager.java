package io.drift.core.store.metadata;

import java.util.HashMap;
import java.util.Map;

import io.drift.core.store.storage.Storable;

public class MetaModelManager {

	private Map<Class<? extends Storable>, MetaModelCreator<? extends Storable>> metaDataCreators = new HashMap<>();

	@SuppressWarnings("unchecked")
	public <STORABLE extends Storable> MetaModelCreator<STORABLE> forModelClass(Class<STORABLE> modelClass) {
		return (MetaModelCreator<STORABLE>) metaDataCreators.get(modelClass);
	}

	public <STORABLE extends Storable> void registerMetaDataCreator(Class<STORABLE> modelClass, MetaModelCreator<STORABLE> metaDataCreator) {
		metaDataCreators.put(modelClass, metaDataCreator);
	}
}
