package io.drift.core.lifecycle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanContainer {

    private Map<Class, BeanDescriptor> descriptors = new HashMap<>();

    private List<Scope> scopes = new ArrayList<>();

    public Object getBean(Class _class) {
        BeanDescriptor descriptor = descriptors.get(_class);
        return descriptor.getScope().getBean(descriptor);
    }

    public void registerModule(Module module) {
        module.registerTo(this);
    }

    public void registerBean(BeanDescriptor descriptor) {
        descriptors.put(descriptor.getBeanClass(), descriptor);
    }

}
