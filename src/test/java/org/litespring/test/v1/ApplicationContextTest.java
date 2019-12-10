package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.FileSystemXmlApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;

import org.litespring.service.v1.PetStoreService;


/**
 * @author YYDCYY
 * @create 2019-12-10
 */
public class ApplicationContextTest {

    @Test
    public void testGetBean() {
        // 接口 与 实现 起名很规范, 简单明了.
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v1.xml");
        //PetStoreService petStoreService=new PetStoreService();
        PetStoreService petStore=(PetStoreService)ctx.getBean("petStore");
        Assert.assertNotNull(petStore);
    }

   @Test
    public void testGetBeanFromFileSystemContext() {
        //hardcode 不好
        //ApplicationContext ctx=new FileSystemXmlApplicationContext("D:\\litespring-workspace\\mylitespring\\src\\test\\resources\\petstore-v1.xml");
        ApplicationContext ctx = new FileSystemXmlApplicationContext("src/test/resources/petstore-v1.xml");
        PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
        Assert.assertNotNull(petStore);
    }
}
