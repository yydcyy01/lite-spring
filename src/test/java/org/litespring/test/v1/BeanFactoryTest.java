package org.litespring.test.v1;

import Factory.BeanDefinition;
import Factory.BeanFactory;
import Factory.support.DefaultBeanFactory;
import org.litespring.service.v1.PetStoreService;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author YYDCYY
 * @create 2019-12-09
 *
 *
 *
 */
public class BeanFactoryTest {
    @Test
    public void testGetBean(){
        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
        BeanDefinition bd = factory.getBeanDefinition("petStore");

        // 导入了静态类, 直接使用
        assertEquals("org.litespring.service.v1.PetStoreService", bd.getBeanClassName());

        PetStoreService petStore = (PetStoreService) factory.getBean("petStore");

        assertNotNull(petStore);
    }
}
