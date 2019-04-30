package io.drift.core.lifecycle;

public class RequestScopedBean {

    private final FlowScopedBean flowScopedBean;

    public RequestScopedBean(FlowScopedBean flowScopedBean) {
        this.flowScopedBean = flowScopedBean;
    }

    public void doSomething() {
        flowScopedBean.doSomething();
    }

    public FlowScopedBean getFlowScopedBean() {
        return flowScopedBean;
    }

}
