package io.drift.ui.config;

import io.drift.core.ui.DriftServer;
import io.drift.ui.infra.GroovyConfigLoader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
 
@Component
public class DriftConfig {

	@Value("${groovy.dir}")
	String groovyDir;
	
	@Value("${server.config.file}")
	String serverConfigFile;
	
	@Bean
	DriftServer getDriftServer() throws Exception {
		GroovyConfigLoader groovyConfigLoader = getGroovyConfigLoader();
		return (DriftServer)groovyConfigLoader.load(serverConfigFile);
	}
	
	@Bean
	GroovyConfigLoader getGroovyConfigLoader() throws Exception {
		return new GroovyConfigLoader(groovyDir, this.getClass().getClassLoader());
	}

}
