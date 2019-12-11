package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Before;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.FileSystemResource;
import org.litespring.service.v1.PetStoreService;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author YYDCYY
 * @create 2019-12-09
 */
public class BeanFactoryTest {
    DefaultBeanFactory factory = null;
    XmlBeanDefinitionReader reader = null;

    @Before
    public void  setUP(){
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
    }

    @Test
    public void testGetBean(){

        reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));


        BeanDefinition bd = factory.getBeanDefinition("petStore");
        assertTrue(bd.isSingleton());
        assertFalse(bd.isPrototype());
        assertEquals(BeanDefinition.SCOPE_DEFAULT, bd.getScope());

        // 导入了静态类, 直接使 用
        assertEquals("org.litespring.service.v1.PetStoreService", bd.getBeanClassName());
        PetStoreService petStore = (PetStoreService) factory.getBean("petStore");
        assertNotNull(petStore);

        PetStoreService petStore1 = (PetStoreService) factory.getBean("petStore");
        assertTrue(petStore.equals(petStore1));
    }
    @Test
    public void testInvalidBean(){
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));

        try {
            // 试图获取一个 invalidBean [ 不存在的 ]
            factory.getBean("invalidBean");
        } catch (BeanCreationException e) {
            //System.out.println("啥  ? ");
            return;
        }
        Assert.fail("expect BeanCreationException  ");

    }

    @Test
    public void testInvalidXML() {
        try {
            reader.loadBeanDefinitions(new FileSystemResource("xxxxx.xml"));
        } catch (BeanDefinitionStoreException e) {
            // TODO: handle exception
            return;
        }
        Assert.fail("expect BeanDefinitionStoreExeption");
    }
}
