package io.drift.core.lifecycle;

import junit.framework.TestCase;
import org.junit.Assert;

public class LifeCycleTest extends TestCase {

    private BeanContainer beanContainer;

    protected void setUp() {
        beanContainer = new BeanContainer();
        beanContainer.registerModule(new Module1());
    }

    public void testBeanCreation() {

        SingletonScopedBean singletonScopedBean = (SingletonScopedBean) beanContainer.getBean(SingletonScopedBean.class);
        Assert.assertNotNull(singletonScopedBean);

        FlowScopedBean flowScopedBean = (FlowScopedBean) beanContainer.getBean(FlowScopedBean.class);
        Assert.assertNotNull(flowScopedBean);
        Assert.assertNotNull(flowScopedBean.getSingletonScopedBean());

        RequestScopedBean requestScopedBean = (RequestScopedBean) beanContainer.getBean(RequestScopedBean.class);
        Assert.assertNotNull(requestScopedBean.getFlowScopedBean());
        Assert.assertNotNull(requestScopedBean.getFlowScopedBean().getSingletonScopedBean());

    }

    public void testSingletonScope() {
        SingletonScopedBean singletonScopedBean = (SingletonScopedBean) beanContainer.getBean(SingletonScopedBean.class);
        SingletonScopedBean singletonScopedBean2 = (SingletonScopedBean) beanContainer.getBean(SingletonScopedBean.class);
        Assert.assertEquals(singletonScopedBean, singletonScopedBean2);

        FlowScopedBean flowScopedBean = (FlowScopedBean) beanContainer.getBean(FlowScopedBean.class);
        FlowScopedBean flowScopedBean2 = (FlowScopedBean) beanContainer.getBean(FlowScopedBean.class);
        Assert.assertNotEquals(flowScopedBean, flowScopedBean2);
        Assert.assertEquals(singletonScopedBean, flowScopedBean.getSingletonScopedBean());

        RequestScopedBean requestScopedBean = (RequestScopedBean) beanContainer.getBean(RequestScopedBean.class);
        RequestScopedBean requestScopedBean2 = (RequestScopedBean) beanContainer.getBean(RequestScopedBean.class);
        Assert.assertNotEquals(requestScopedBean, requestScopedBean2);
        Assert.assertEquals(singletonScopedBean, requestScopedBean.getFlowScopedBean().getSingletonScopedBean());



    }

    public void testStoreBackedBean() {



    }

}
