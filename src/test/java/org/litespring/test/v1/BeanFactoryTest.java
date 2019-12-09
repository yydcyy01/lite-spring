package org.litespring.test.v1;

import org.junit.Assert;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinition;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.service.v1.PetStoreService;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author YYDCYY
 * @create 2019-12-09
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
    @Test
    public void testInvalidBean(){
        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
        try {
            // 试图获取一个 invalidBean [ 不存在的 ]
            factory.getBean("invalidBean");
        } catch (BeanCreationException e) {
            //System.out.println("啥  ? ");
            return;
        }
        Assert.fail("expect BeanCreationException ");

    }

    @Test
    public void testInvalidXML() {
        try {
            new DefaultBeanFactory("xxxxx.xml");
        } catch (BeanDefinitionStoreException e) {
            // TODO: handle exception
            return;
        }
        Assert.fail("expect BeanDefinitionStoreExeption");
    }
}
