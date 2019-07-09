package io.drift.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan(basePackageClasses = DriftCoreAutoConfig.class)
@EnableAsync
public class DriftCoreAutoConfig {

}
