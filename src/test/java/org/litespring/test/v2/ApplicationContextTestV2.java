package org.litespring.test.v2;

import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.dao.v2.AccountDao;
import org.litespring.dao.v2.ItemDao;
import org.litespring.service.v2.PetStoreService;

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

    }
}
