package io.drift.ui;

import io.drift.core.store.IDGenerator;
import io.drift.core.store.IDGeneratorUUIDImpl;
import io.drift.core.store.ModelStore;
import io.drift.core.store.serialization.JsonModelSerializer;
import io.drift.core.store.storage.FileSystemModelStorage;
import io.drift.jdbc.domain.data.DBDelta;
import io.drift.jdbc.domain.metadata.DBMetaData;
import io.drift.ui.config.DefaultDriftSpringSecurityConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.nio.file.Paths;

@SpringBootApplication
@Import({ DefaultDriftSpringSecurityConfig.class})
public class AcmeDriftConfig {

    @Bean
    ModelStore getModelStore() {
        ModelStore modelStore = new ModelStore();
        JsonModelSerializer modelSerializer = new JsonModelSerializer();
        modelStore.getSerializationManager().registerSerializer(modelSerializer);
        modelStore.getModelStorageManager().registerStorage(new FileSystemModelStorage(Paths.get("store")));
        return modelStore;
    }

    @Bean
    IDGenerator idGenerator() {
        return new IDGeneratorUUIDImpl();
    }

}
