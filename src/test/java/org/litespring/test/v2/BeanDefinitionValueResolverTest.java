package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.support.BeanDefinitionValueResolver;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.dao.v2.AccountDao;

/**
 * @author YYDCYY
 * @create 2019-12-12
 */
public class BeanDefinitionValueResolverTest {

    /**
     * 实现 bean 名称 变成 bean 实例.
     *  BeanFactory 有个接口 getBean() .
     */
    @Test
    public void testResolveRuntimeBeanReference(){
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));

        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);
        RuntimeBeanReference reference = new RuntimeBeanReference("accountDao");
        // 试图将 Refference 值变成实例.
        Object value = resolver.resolveValueIfNecessary(reference);

        //测试不为空
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof AccountDao);

    }
}
