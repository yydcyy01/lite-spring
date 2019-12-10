package org.litespring.test.v1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author YYDCYY
 * @create 2019-12-10
 * JUnit4 注解
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationContextTest.class,
        BeanFactoryTest.class})
public class V1AllTests {

}
