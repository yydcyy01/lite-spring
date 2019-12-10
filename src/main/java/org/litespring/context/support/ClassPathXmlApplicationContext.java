package org.litespring.context.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;

/**
 * @author YYDCYY
 * @create 2019-12-10
 * 02 从零开始造 Spring.md 笔记中的类图
 * 详情见
 */
public class ClassPathXmlApplicationContext implements ApplicationContext {

    private DefaultBeanFactory factory = null;

    /**
     * 构造器
     * 按类路径加载配置文件
     * @param configFile
     */
    public ClassPathXmlApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(configFile);
    }


    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }
}
