package io.drift.ui;

import java.nio.file.Paths;

import io.drift.core.config.DriftEngine;
import io.drift.ui.config.DefaultDriftSpringSecurityConfig;
import io.drift.ui.config.DriftServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import io.drift.core.store.IDGenerator;
import io.drift.core.store.IDGeneratorUUIDImpl;
import io.drift.core.store.ModelStore;
import io.drift.core.store.serialization.JsonModelSerializer;
import io.drift.core.store.storage.FileSystemModelStorage;
import io.drift.jdbc.config.DriftJdbcConfig;

@SpringBootApplication
@Import({ DriftJdbcConfig.class, DriftServerConfig.class, DefaultDriftSpringSecurityConfig.class})
public class AcmeDriftConfig {

    @Autowired
    DriftEngine driftEngine;

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
