package org.litespring.context.support;

import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

/**
 * @author YYDCYY
 * @create 2019-12-10
 * 02 从零开始造 Spring.md 笔记中的类图
 * 详情见
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    public ClassPathXmlApplicationContext(String configFile) {
        super(configFile);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected Resource getResourceByPath(String path) {
        //return new ClassPathResource(path);
        return new ClassPathResource(path, this.getBeanClassLoader());
    }
}
