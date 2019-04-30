package io.drift.core.lifecycle;

public class SingletonScope extends AbstractScope implements  Scope {

    @Override
    public Object getBean(BeanDescriptor descriptor) {
        return getOrCreate(descriptor);
    }

}
