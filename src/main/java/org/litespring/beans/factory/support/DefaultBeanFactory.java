package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.util.ClassUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author YYDCYY
 * @create 2019-12-09
 */
public class DefaultBeanFactory implements ConfigurableBeanFactory, BeanDefinitionRegistry{

    /**
     * 注 : 这里为了省事, 并没有处理线程安全问题, 仅仅使用 HashMap 保存了下来
     */
   private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);

   // 构造器
    public DefaultBeanFactory () {

    }

    public void registerBeanDefinition(String beanID, BeanDefinition bd) {
        this.beanDefinitionMap.put(beanID, bd);
    }

    public BeanDefinition getBeanDefinition(String beanID) {
        return this.beanDefinitionMap.get(beanID);
    }



    /**
     * 作用 : BeanDefinition 转变成 bean instance实例.
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

    public void setBeanClassLoader(ClassLoader beanClassLoader) {

    }

    public ClassLoader getBeanClassLoader() {
        return null;
    }
}
