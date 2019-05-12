package io.drift.ui.config;

import io.drift.ui.app.flux.FluxLocation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(WicketConfig.class)
@ComponentScan(basePackageClasses = FluxLocation.class)
public class DriftServerAutoConfig {

    @Bean
    WicketComponentRegistry wicketComponentRegistry() {
        return new WicketComponentRegistry();
    }

    @Bean
    WicketComponentBeanPostProcessor wicketComponentBeanPostProcessor() {
        return new WicketComponentBeanPostProcessor(wicketComponentRegistry());
    }

}
