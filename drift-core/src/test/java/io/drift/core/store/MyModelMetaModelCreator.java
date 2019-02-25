package io.drift.core.store;

import io.drift.core.store.metadata.MetaModelCreator;

public class MyModelMetaModelCreator extends MetaModelCreator<MyModel> {

	@Override
	public MetaModel createFrom(MyModel model) {
		MetaModel metaModel = new MetaModel();
		metaModel.setModelType(model.getClass().toString());
		return metaModel;
	}

}
