package com.github.driftserver.core;

import com.github.driftserver.core.metamodel.ModelStore;
import com.github.driftserver.core.recording.storage.RecordingStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.inject.Inject;

@Configuration
@ComponentScan(basePackageClasses = DriftCoreAutoConfig.class)
@EnableAsync
public class DriftCoreAutoConfig {

    @Inject
    ModelStore modelStore;

    @Bean
    RecordingStorage recordingStorage() {
        return new RecordingStorage(modelStore);
    }

}
