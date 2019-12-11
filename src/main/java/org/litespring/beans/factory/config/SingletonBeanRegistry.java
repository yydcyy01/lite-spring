package org.litespring.beans.factory.config;

/**
 * @author YYDCYY
 * @create 2019-12-10
 */
public interface SingletonBeanRegistry {

    //注册 Singleton
    void registerSingleton(String beanName,Object singletonObject);

    Object getSingleton(String beanName);
}
