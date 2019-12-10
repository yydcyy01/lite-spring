package org.litespring.beans.factory.support;


import org.litespring.beans.BeanDefinition;

/**
 * @author YYDCYY
 * @create 2019-12-10
 */
public class GenericBeanDefinition implements BeanDefinition {
    private String id;
    private String beanClassName;

    //构造器
    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    public String getBeanClassName() {

        return this.beanClassName;
    }
}
