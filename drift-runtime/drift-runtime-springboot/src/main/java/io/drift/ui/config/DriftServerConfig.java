package io.drift.ui.config;

import io.drift.core.config.DriftEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriftServerConfig {

    @Autowired
    DriftEngine driftEngine;


}
