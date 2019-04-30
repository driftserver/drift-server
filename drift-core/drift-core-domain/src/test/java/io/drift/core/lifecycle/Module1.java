package io.drift.core.lifecycle;

public class Module1 implements  Module {
    @Override
    public void registerTo(BeanContainer beanContainer) {

        FlowScope flowScope = new FlowScope();
        SingletonScope singletonScope = new SingletonScope();
        RequestScope requestScope = new RequestScope();

        beanContainer.registerBean(describeSingletonScopedBean(singletonScope));
        beanContainer.registerBean(describeFlowScopedBean(beanContainer, flowScope));
        beanContainer.registerBean(describeRequestScopedBean(beanContainer, requestScope));

    }

    private BeanDescriptor describeRequestScopedBean(BeanContainer beanContainer, RequestScope requestScope) {
        return new BeanDescriptor(
            RequestScopedBean.class,
            requestScope,
            () -> {
                FlowScopedBean flowScopedBean = (FlowScopedBean) beanContainer.getBean(FlowScopedBean.class);
                return new RequestScopedBean(flowScopedBean);
            }
        );
    }

    private BeanDescriptor describeSingletonScopedBean(SingletonScope singletonScope) {
        return new BeanDescriptor(
                SingletonScopedBean.class,
                singletonScope,
                () -> new SingletonScopedBean()
        );
    }

    private BeanDescriptor describeFlowScopedBean(BeanContainer beanContainer, FlowScope flowScope) {
        return new BeanDescriptor(
                FlowScopedBean.class,
                flowScope,
                () -> {
                    SingletonScopedBean singletonScopedBean = (SingletonScopedBean) beanContainer.getBean(SingletonScopedBean.class);
                    return new FlowScopedBean(singletonScopedBean);
                }
        );
    }
}
