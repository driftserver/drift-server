package io.drift.ui;

import io.drift.ui.app.flux.FluxLocation;
import io.drift.ui.config.WicketConfig;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(WicketConfig.class)
@ComponentScan
@AutoConfigurationPackage
public class DriftServerAutoConfig {



}
