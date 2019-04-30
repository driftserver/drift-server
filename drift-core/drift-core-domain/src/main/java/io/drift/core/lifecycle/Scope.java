package io.drift.core.lifecycle;

public interface Scope {
    Object getBean(BeanDescriptor descriptor);
}
