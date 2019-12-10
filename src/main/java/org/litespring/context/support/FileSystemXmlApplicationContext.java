package org.litespring.context.support;

import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

/**
 * @author YYDCYY
 * @create 2019-12-10
 */
public class FileSystemXmlApplicationContext implements ApplicationContext {
    private DefaultBeanFactory factory = null;

    /**
     * 构造器
     * 按类路径加载配置文件
     * @param configFile
     */
    public FileSystemXmlApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new FileSystemResource(configFile);
        reader.loadBeanDefinitions(resource);
    }



    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }
}
