package io.drift.ui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.drift.core.config.DriftEngine;

@Component
public class DriftConfig {

	@Bean
	DriftEngine getDriftEngine() {
		return new DriftEngine();
	}

}
