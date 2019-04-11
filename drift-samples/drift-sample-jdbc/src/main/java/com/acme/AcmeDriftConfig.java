package com.acme;

import java.nio.file.Paths;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.drift.core.store.IDGenerator;
import io.drift.core.store.IDGeneratorUUIDImpl;
import io.drift.core.store.ModelStore;
import io.drift.core.store.serialization.JsonModelSerializer;
import io.drift.core.store.storage.FileSystemModelStorage;
import io.drift.jdbc.cli.DriftJdbcCliConfig;
import io.drift.jdbc.config.DriftJdbcConfig;

@Configuration
@Import({ DriftJdbcConfig.class, DriftJdbcCliConfig.class })
public class AcmeDriftConfig {

	@Bean
	ModelStore getModelStore() {
		ModelStore modelStore = new ModelStore();
		modelStore.getSerializationManager().registerSerializer(new JsonModelSerializer());
		modelStore.getModelStorageManager().registerStorage(new FileSystemModelStorage(Paths.get("store")));
		return modelStore;
	}

	@Bean
	IDGenerator idGenerator() {
		return new IDGeneratorUUIDImpl();
	}

}
