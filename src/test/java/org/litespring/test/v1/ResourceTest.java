package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author YYDCYY
 * @create 2019-12-10
 */
public class ResourceTest {

    @Test
    public void testClassPathResource() throws IOException {
        Resource resource=new ClassPathResource("petstore-v1.xml");
        InputStream inputStream=null;
        try {
            inputStream=resource.getInputStream();
            //注意：这个测试其实并不充分   充分测试应该检查 inputStream 内容读出来
            Assert.assertNotNull(inputStream);
        } finally {
            if (inputStream!=null) {
                inputStream.close();
            }
        }
    }

    @Test
    public void testFileSystemResource() throws IOException {
        //测试类中这样写是不对的. 与环境相关性太大
        //Resource r=new FileSystemResource("/Users/yuyang/IdeaProjects/lite-spring/src/test/resource/petstore-v1.xml");
        // 写相对路径要好一点
        Resource r=new FileSystemResource("src/test/resource/petstore-v1.xml");


        InputStream is=null;
        try {
            is=r.getInputStream();
            Assert.assertNotNull(is);
        } finally {
            if (is!=null) {
                is.close();
            }
        }
    }
}