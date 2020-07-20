package com.github.driftserver.plugin.elasticsearch;

import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = DriftElasticSearchAutoConfig.class)
@AutoConfigurationPackage
public class DriftElasticSearchAutoConfig {

    public static final String ELASTICSEARCH_SUBSYSTEM_TYPE = "elasticsearch";

}
