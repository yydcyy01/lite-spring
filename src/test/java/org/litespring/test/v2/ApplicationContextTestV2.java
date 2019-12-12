package org.litespring.test.v2;

import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.dao.v2.AccountDao;
import org.litespring.dao.v2.ItemDao;
import org.litespring.service.v2.PetStoreService;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author YYDCYY
 * @create 2019-12-11
 */

public class ApplicationContextTestV2 {

    @Test
    public void testGetBeanPorperty() {
        ApplicationContext ctx=new ClassPathXmlApplicationContext("petstore-v2.xml");

        PetStoreService petStoreService=(PetStoreService)ctx.getBean("petStore");

        assertNotNull(petStoreService.getAccountDao());
        assertNotNull(petStoreService.getItemDao());

        // 确保就是对象实例
        assertTrue(petStoreService.getAccountDao() instanceof AccountDao);
        assertTrue(petStoreService.getItemDao() instanceof ItemDao);

        assertEquals("yuyang", petStoreService.getOwner());
        //assertEquals(2, petStoreService.getVersion());
    }
}
