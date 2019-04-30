package io.drift.core.lifecycle;

@FunctionalInterface
public interface BeanConstructor {
    Object create();
}
