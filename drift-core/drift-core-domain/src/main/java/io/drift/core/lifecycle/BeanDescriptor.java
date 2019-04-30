package io.drift.core.lifecycle;

public class BeanDescriptor {

    private BeanConstructor constructor;

    private Scope scope;

    private Class beanClass;

    public BeanDescriptor(Class beanClass, Scope scope, BeanConstructor constructor) {
        this.constructor = constructor;
        this.scope = scope;
        this.beanClass = beanClass;
    }

    public BeanConstructor getConstructor() {
        return constructor;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public Scope getScope() {
        return scope;
    }

}
