package org.litespring.beans.factory.support;

import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;

/**
 * @author YYDCYY
 * @create 2019-12-12
 */
public class BeanDefinitionValueResolver {
    private final DefaultBeanFactory beanFactory;

    public BeanDefinitionValueResolver(DefaultBeanFactory factory) {
        this.beanFactory = factory;
    }

    /**
     * 模仿 Spring 起名, 寓意 :
     *
     * 注 : 这里只写了两种类型 :
     * RuntimeBeanReference TypedStringValue
     * 其他类型被写成了 TODO
     * 认为还没有实现 防止程序偶然出错, 强制出错
     * @param Object
     * @return
     */
    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            String refName = ref.getBeanName();
            Object bean = this.beanFactory.getBean(refName);
            return bean;
        }else if (value instanceof TypedStringValue) {
            // 如果是 TypedStringValue 子类类型, 直接取出.
            return ((TypedStringValue)value).getValue();
        }else {
            //TODO
            throw new RuntimeException("the value " + value +" has not implemented");
        }
    }
}
