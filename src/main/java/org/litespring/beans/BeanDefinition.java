package org.litespring.beans;

import java.util.List;

/**
 * @author YYDCYY
 * @create 2019-12-09
 */
public interface BeanDefinition {

    public static final String SCOPE_SINGLETON="singleton";
    public static final String SCOPE_PROTOTYPE="prototype";
    // 默认为空串
    public static final String SCOPE_DEFAULT="";

    public boolean isSingleton();
    public boolean isPrototype();

    String getScope();
    void setScope(String scope);

    public String getBeanClassName();
    public List<PropertyValue> getPropertyValues();

}
