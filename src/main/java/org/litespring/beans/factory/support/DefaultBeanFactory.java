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
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
        implements ConfigurableBeanFactory, BeanDefinitionRegistry{

    /**
     * 注 : 这里为了省事, 并没有处理线程安全问题, 仅仅使用 HashMap 保存了下来
     */
   private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);
   private ClassLoader beanClassLoader;
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
            return null;
        }

        if (bd.isSingleton()){
            Object bean = this.getSingleton(beanID);
            if (bean == null){
                bean = createBean(bd);
                // 注册 Singleton 至 map 中
                this.registerSingleton(beanID, bean);
            }
            return bean;
        }
        // 不是单例模式, 则直接创建
        return createBean(bd);
    }

    // 除了创建 Bean, 还应该有其他功能, 比如 setter 注入等. 再说
    private Object createBean(BeanDefinition bd) {
        ClassLoader cl = this.getBeanClassLoader();
        String beanClassName = bd.getBeanClassName();

        try {
            //加载, 通过反射方式创建.
            //有个假设 : 类有缺省无参参数 [ 后续完善处理. ]
            Class<?> clz = cl.loadClass(beanClassName);
            return clz.newInstance();
            //todo 后续实现构造函数注入 这里需要改动
        } catch (Exception e) {
            throw new BeanCreationException("create bean for " + beanClassName + " failed ", e);
        }
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader == null ? ClassUtils.getDefaultClassLoader() : this.beanClassLoader;
    }
}
