package io.drift.core.config;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import io.drift.core.api.FocalArea;

@Configuration
public class DriftCoreConfig {

	@Bean
	public static BeanFactoryPostProcessor driftScopeBeanFactoryPostProcessor() {
		return new DriftScopeBeanFactoryPostProcessor();
	}

	// @DriftSessionScoped
	@Scope(value = DriftSessionScope.DRIFT_SESSION_SCOPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
	@Bean
	FocalArea focalArea() {
		return new FocalArea();
	}

}
