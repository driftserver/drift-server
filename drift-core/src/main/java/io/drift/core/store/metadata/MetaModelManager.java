package io.drift.core.store.metadata;

import io.drift.core.api.Model;

import java.util.HashMap;
import java.util.Map;

public class MetaModelManager {

	private Map<Class<? extends Model>, MetaModelCreator<? extends Model>> metaDataCreators = new HashMap<>();

	public <M extends Model> void registerMetaDataCreator(Class<M> modelClass, MetaModelCreator<M> metaDataCreator) {
		metaDataCreators.put(modelClass, metaDataCreator);
	}

	@SuppressWarnings("unchecked")
	public <M extends Model> MetaModelCreator<M> forModelClass(Class<M> modelClass) {
		return (MetaModelCreator<M>) metaDataCreators.get(modelClass);
	}
}
