package com.github.driftserver.ui.config;

import com.github.driftserver.core.WicketComponentFactory;
import com.github.driftserver.core.WicketComponentFactoryMethod;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Component
public class WicketComponentBeanPostProcessor implements BeanPostProcessor {

    private final WicketComponentRegistry componentRegistry;

    public WicketComponentBeanPostProcessor(WicketComponentRegistry componentRegistry) {
        this.componentRegistry = componentRegistry;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().getAnnotation(WicketComponentFactory.class) != null) {
            List<Method> methods = Arrays.asList(bean.getClass().getMethods());
            for (final Method method : methods) {
                if (method.isAnnotationPresent(WicketComponentFactoryMethod.class)) {
                    WicketComponentFactoryMethod annotation = method.getAnnotation(WicketComponentFactoryMethod.class);
                    componentRegistry.register(annotation.dataType(), annotation.viewType(), bean, method);
                }
            }
        }
        return bean;
    }

}
