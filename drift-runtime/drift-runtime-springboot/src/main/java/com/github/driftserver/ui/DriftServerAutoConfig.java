package com.github.driftserver.ui;

import com.github.driftserver.ui.config.WicketConfig;
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
