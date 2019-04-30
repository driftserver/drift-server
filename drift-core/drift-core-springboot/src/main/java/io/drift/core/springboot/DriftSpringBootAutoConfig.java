package io.drift.core.springboot;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = DriftSpringBootAutoConfig.class)
public class DriftSpringBootAutoConfig {
}
