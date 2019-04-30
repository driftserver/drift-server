package io.drift.core.lifecycle;

public class RequestScope extends  AbstractScope implements  Scope {
    @Override
    public Object getBean(BeanDescriptor descriptor) {
        return create(descriptor);
    }
}
