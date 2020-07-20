package com.github.driftserver.plugin.filesystem;

import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = DriftFileSystemAutoConfig.class)
@AutoConfigurationPackage
public class DriftFileSystemAutoConfig {

    public static final String FILESYSTEM_SUBSYSTEM_TYPE = "filesystem";

}
