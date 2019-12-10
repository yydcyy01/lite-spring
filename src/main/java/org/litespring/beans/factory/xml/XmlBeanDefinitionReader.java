package org.litespring.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * @author YYDCYY
 * @create 2019-12-10
 * 读取 xml 和解析 xml 职责被拿走分离了.
 */
public class XmlBeanDefinitionReader {

    public static final String ID_ATTRIBUTE="id";
    public static final String CLASS_ATTRIBUTE="class";

    BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry=registry;
    }

    /**
     * 解析传进来的 xml 文件
     * 问题是怎么变成InputStream ? 通过类加载, dom4j 便利处理
     * @param configFile
     */
    public void loadBeanDefinitions(String configFile) {
        InputStream is = null;
        try {
            ClassLoader cl = ClassUtils.getDefaultClassLoader();
            is = cl.getResourceAsStream(configFile);
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);

            Element root = doc.getRootElement(); // <beans> 获取根节点
            Iterator<Element> iter = root.elementIterator();
            while (iter.hasNext()){
                //便利 beans 标签下的 bean
                Element ele = (Element) iter.next();
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                // BeanDefinition 为接口, 需要自己实现一个类
                BeanDefinition bd = new GenericBeanDefinition(id, beanClassName);
                this.registry.registerBeanDefinition(id, bd);
            }
        } catch (DocumentException e) {
            throw new BeanDefinitionStoreException("IOException parsing XML document is not exist ");
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}