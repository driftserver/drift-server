package io.drift.core.config;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.*;

import io.drift.core.api.FocalArea;

@Configuration
@ComponentScan(basePackageClasses = DriftCoreConfig.class)
public class DriftCoreConfig {

	// @DriftSessionScoped
	@Scope(value = DriftSessionScope.DRIFT_SESSION_SCOPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
	@Bean
	FocalArea focalArea() {
		return new FocalArea();
	}

}
