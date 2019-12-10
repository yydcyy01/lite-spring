package org.litespring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author YYDCYY
 * @create 2019-12-10
 */
public class ClassPathResource implements Resource {
    public ClassPathResource(String s) {
    }

    public InputStream getInputStream() throws IOException {
        return null;
    }

    public String getDescription() {
        return null;
    }
}
