package io.drift.plugin.jdbc;

import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = DriftJDBCAutoConfig.class)
@AutoConfigurationPackage
public class DriftJDBCAutoConfig {

    public static final String JDBC_SUBSYSTEM_TYPE = "jdbc";


}
