package org.litespring.beans.factory.config;

/**
 * @author YYDCYY
 * @create 2019-12-11
 */
public class RuntimeBeanReference {

    private final String beanName;
    public RuntimeBeanReference(String beanName) {
        this.beanName=beanName;
    }

    public String getBeanName() {
        return this.beanName;
    }
}