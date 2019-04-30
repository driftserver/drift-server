package io.drift.core.lifecycle;

public class FlowScopedBean {

    private SingletonScopedBean singletonScopedBean;

    public FlowScopedBean(SingletonScopedBean singletonScopedBean) {
        this.singletonScopedBean = singletonScopedBean;
    }

    public void doSomething() {
        singletonScopedBean.doSomething();
    }

    public SingletonScopedBean getSingletonScopedBean() {
        return singletonScopedBean;
    }
}
