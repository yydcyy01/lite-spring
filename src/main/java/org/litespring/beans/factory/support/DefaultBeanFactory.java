package org.litespring.beans.factory.support;

import org.litespring.beans.factory.BeanDefinition;
import org.litespring.beans.factory.BeanFactory;
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
public class DefaultBeanFactory implements BeanFactory {

   public static final String ID_ATTRIBUTE = "id";
   public static final String CLASS_ATTRIBUTE = "class";
   private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);

   // 构造器
    public DefaultBeanFactory (String configFile) {
        loadBeanDefinition(configFile);
    }

    /**
     * 解析传进来的 xml 文件
     * 问题是怎么变成InputStream ? 通过类加载, dom4j 便利处理
     * @param configFile
     */
    private void loadBeanDefinition(String configFile) {
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
                this.beanDefinitionMap.put(id, bd);
            }
        } catch (DocumentException e) {
            // TODO 跑出异常
            e.printStackTrace();
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
            return null;
        }
        ClassLoader cl = ClassUtils.getDefaultClassLoader();
        String beanClassName = bd.getBeanClassName();
        //" org.litespring.service.v1.PetStoreService " 配置文件中的这个东西
        try {
            //加载, 通过反射方式创建.
            //有个假设 : 类有缺省无参参数 [ 后续完善处理. ]
            Class<?> clz = cl.loadClass(beanClassName);
            return clz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e){
            e.printStackTrace();
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }

        return null;
    }
}
