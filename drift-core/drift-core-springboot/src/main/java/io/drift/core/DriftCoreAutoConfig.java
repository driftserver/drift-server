package io.drift.core;

import io.drift.core.metamodel.ModelStore;
import io.drift.core.recording.RecordingStorage;
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
