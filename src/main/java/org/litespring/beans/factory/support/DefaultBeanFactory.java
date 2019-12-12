package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
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

    private Object createBean(BeanDefinition bd) {
        //创建实例
        Object bean = instantiateBean(bd);
        //设置属性  设置 setter , 通过 setter 注入
        populateBean(bd, bean);

        return bean;

    }


    /**
     * 由 bean 定义的 className, 通过反射创建出来.
     * 除了创建 Bean, 还应该有其他功能, 比如 setter 注入等. 再说
     * @param bd
     * @return
     */
    private Object instantiateBean(BeanDefinition bd) {
        ClassLoader cl = this.getBeanClassLoader();
        String beanClassName = bd.getBeanClassName();

        //加载, 通过反射方式创建.
        //有个假设 : 类有缺省无参参数 [ 后续完善处理. ]
        try {
            Class<?> clz = cl.loadClass(beanClassName);
            return clz.newInstance();
            //todo 后续实现构造函数注入 这里需要改动

        } catch (Exception e) {
            throw new BeanCreationException("create bean for "+ beanClassName +" failed",e);
        }
    }

    /**
     * 希望在 populateBean 中调用方法.
     * 详情见笔记中设计的类图.
     * ①
     * @param bd
     * @param bean
     */
    protected void populateBean(BeanDefinition bd, Object bean){
        List<PropertyValue> pvs = bd.getPropertyValues();

        if (pvs == null || pvs.isEmpty()) {
            return;
        }

        //若不为空, 做业务逻辑
        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
        try{

            //便利 Beans 标签
            for (PropertyValue pv : pvs){
                String propertyName = pv.getName();
                Object originalValue = pv.getValue();
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);
                /**
                 * 假设现在 originalValue 表示的是 ref = accountDao , 已经通过 resolve 得到了 AccountDao 对象
                 * 接下来怎么办? 如何去调用 PetStore 的 setAccountDao 方法?
                 *
                 * 分析 : 现在知道propertyName [bean 名字]
                 * 使用 java bean 类工具实现.
                 */
                //org.litespring.service.v2.PetStoreService
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                // 属性描述器 获取 beanInfo中 [ getxxx(), getxxx() ]
                PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();

                /**
                 *  <bean id="petStore"
                 *           class="org.litespring.service.v2.PetStoreService" >
                 *
                 *         <property name="accountDao" ref="accountDao"/>
                 *         <property name="itemDao" ref="itemDao"/>
                 *     </bean>
                 *
                 *     <bean id="accountDao"  class="org.litespring.dao.v2.AccountDao">
                 *     </bean>
                 *
                 *     <bean id="itemDao" class="org.litespring.dao.v2.ItemDao">
                 *     </bean>

                 */
                //便利 Bean 标签中的 accountDao itemDao
                for (PropertyDescriptor pd : pds) {
                    // 若 当前property 名[itemDao 为例] 与 propertyName相同 [ 之前获取的 ]
                    if(pd.getName().equals(propertyName)){
                        pd.getWriteMethod().invoke(bean, resolvedValue);
                        break;
                    }
                }
            }
        }catch(Exception ex){
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" + bd.getBeanClassName() + "]", ex);
        }
    }


    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader == null ? ClassUtils.getDefaultClassLoader() : this.beanClassLoader;
    }
}
