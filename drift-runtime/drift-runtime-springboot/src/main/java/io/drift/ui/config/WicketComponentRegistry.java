package io.drift.ui.config;

import org.apache.wicket.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@org.springframework.stereotype.Component
public class WicketComponentRegistry {

    private class RegistryKey {
        private Class<?> dataType;
        private Class<?> presentationType;

        public RegistryKey(Class<?> dataType, Class<?> presentationType) {
            this.dataType = dataType;
            this.presentationType = presentationType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RegistryKey that = (RegistryKey) o;
            return Objects.equals(dataType, that.dataType) &&
                    Objects.equals(presentationType, that.presentationType);
        }

        @Override
        public int hashCode() {
            return Objects.hash(dataType, presentationType);
        }
    }

    private class RegistryEntry {
        private Object bean;
        private Method method;
        public RegistryEntry(Object bean, Method method) {
            this.bean = bean;
            this.method = method;
        }

        public Object getBean() {
            return bean;
        }

        public Method getMethod() {
            return method;
        }
    }

    private Map<RegistryKey, RegistryEntry> entryMap = new HashMap<>();

    public void register(Class dataType, Class presentationType, Object bean, Method method) {
        entryMap.put(new RegistryKey(dataType, presentationType), new RegistryEntry(bean, method));
    }

    public Component render(String id, Object data, Class presentationClass, Object... additionalArgs) {
        Class dataType = data.getClass();
        RegistryEntry methodAndBeanTuple = entryMap.get(new RegistryKey(dataType, presentationClass));

        Object[] args = new Object[additionalArgs.length+2];
        args[0] = id;
        args[1] = data;
        for(int idx = 0; idx < additionalArgs.length; idx++) {
            args[idx+2] = additionalArgs[idx];
        }

        try {
            return (Component) methodAndBeanTuple.getMethod().invoke(methodAndBeanTuple.getBean(), args);
        } catch (Exception e) {
            throw new IllegalArgumentException("problem rendering widget. id=" + id + ", data =" + data, e);
        }
    }

}
