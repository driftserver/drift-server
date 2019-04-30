package io.drift.core.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class DriftAnnotationPostProcessor implements BeanPostProcessor {

    private final DriftEngine driftEngine;

    public DriftAnnotationPostProcessor(DriftEngine driftEngine) {
        this.driftEngine = driftEngine;
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String name) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String name) throws BeansException {
        if (bean instanceof EnginePlugin) {
            EnginePlugin plugin = (EnginePlugin) bean;
            driftEngine.registerPlugin(plugin);
        }

        return bean;
    }
}
