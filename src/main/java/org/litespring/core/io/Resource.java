package org.litespring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author YYDCYY
 * @create 2019-12-10
 */
public interface Resource {
    public InputStream getInputStream() throws IOException;

    public String getDescription();
}