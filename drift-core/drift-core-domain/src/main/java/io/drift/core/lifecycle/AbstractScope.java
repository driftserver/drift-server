package io.drift.core.lifecycle;

import java.util.HashMap;
import java.util.Map;

abstract public class AbstractScope implements Scope {

    private Map<Class, Object> objects = new HashMap<>();

    protected Object getOrCreate(BeanDescriptor descriptor) {
        Object object = objects.get(descriptor.getBeanClass());
        if (object == null) {
            object = create(descriptor);
            objects.put(descriptor.getBeanClass(), object);
        }
        return object;

    }

    protected Object create(BeanDescriptor descriptor) {
        return descriptor.getConstructor().create();
    }


}
