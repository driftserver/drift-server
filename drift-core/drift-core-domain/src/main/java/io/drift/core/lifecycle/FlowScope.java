package io.drift.core.lifecycle;

public class FlowScope extends AbstractScope implements Scope {
    @Override
    public Object getBean(BeanDescriptor descriptor) {
        return create(descriptor);
    }
}
