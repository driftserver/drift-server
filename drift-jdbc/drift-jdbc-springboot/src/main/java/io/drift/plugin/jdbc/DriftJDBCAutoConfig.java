package io.drift.plugin.jdbc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = DriftJDBCAutoConfig.class)
public class DriftJDBCAutoConfig {
}
