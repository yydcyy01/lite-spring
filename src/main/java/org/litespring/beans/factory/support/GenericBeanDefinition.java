package org.litespring.beans.factory.support;


import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YYDCYY
 * @create 2019-12-10
 */
public class GenericBeanDefinition implements BeanDefinition{

    private String id;
    private String beanClassName;
    private boolean singleton=true;
    private boolean prototype=false;
    private String scope = SCOPE_DEFAULT; // 扩充 BeanDefinition 类

    List<PropertyValue> propertyValues=new ArrayList<PropertyValue>();

    public GenericBeanDefinition(String id,String beanClassName) {
        this.id=id;
        this.beanClassName=beanClassName;
    }

    public GenericBeanDefinition() {

    }
    public String getBeanClassName() {
        return this.beanClassName;
    }

    public boolean isSingleton() {
        return this.singleton;
    }

    public boolean isPrototype() {
        return this.prototype;
    }

    //需要判断 Scope, 故加
    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope=scope;
        //SCOPE 值若为 null / scope 都是记录我单例模式
        this.singleton=SCOPE_SINGLETON.equals(scope)||SCOPE_DEFAULT.equals(scope);
        this.prototype=SCOPE_PROTOTYPE.equals(scope);
    }

    public List<PropertyValue> getPropertyValues() {
        return this.propertyValues;
    }

}
