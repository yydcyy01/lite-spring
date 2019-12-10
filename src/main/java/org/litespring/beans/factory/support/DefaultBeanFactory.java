package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author YYDCYY
 * @create 2019-12-09
 */
public class DefaultBeanFactory implements BeanFactory , BeanDefinitionRegistry{

   public static final String ID_ATTRIBUTE = "id";
   public static final String CLASS_ATTRIBUTE = "class";
   private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);

   // 构造器
    public DefaultBeanFactory (String configFile) {
        loadBeanDefinition(configFile);
    }



    public BeanDefinition getBeanDefinition(String beanID) {
        return this.beanDefinitionMap.get(beanID);
    }



    /**
     * 作用 : BeanDefinition 转变成 bean instance实例.
     * 如何实现 ?
     * @param beanID
     * @return
     */
    public Object getBean(String beanID) {
        BeanDefinition bd = this.getBeanDefinition(beanID);
        if (bd == null){
            throw new BeanCreationException("Bean Definition does not exist  ");
        }
        ClassLoader cl = ClassUtils.getDefaultClassLoader();
        String beanClassName = bd.getBeanClassName();
        //" org.litespring.service.v1.PetStoreService " 配置文件中的这个东西
        try {
            //加载, 通过反射方式创建.
            //有个假设 : 类有缺省无参参数 [ 后续完善处理. ]
            Class<?> clz = cl.loadClass(beanClassName);
            return clz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" + bd.getBeanClassName() + "]", e);
        }
    }

    public void registerBeanDefinition(String id, BeanDefinition bd) {

    }
}
