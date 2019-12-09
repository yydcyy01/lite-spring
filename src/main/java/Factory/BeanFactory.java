package Factory;

/**
 * @author YYDCYY
 * @create 2019-12-09
 */
public interface BeanFactory {

    // 通用, user, 啥啥啥的
    Object getBean(String string);

    BeanDefinition getBeanDefinition(String beanID);
}
