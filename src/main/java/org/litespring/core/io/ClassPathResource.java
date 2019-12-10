package org.litespring.core.io;


import org.litespring.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author YYDCYY
 * @create 2019-12-10
 */
public class ClassPathResource implements Resource {

    private String path;
    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, null);
    }

    public ClassPathResource(String path,ClassLoader classLoader) {
        this.path = path;
        // 不为空就用你, 为空就用 getDefaultClassLoader
        this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
    }

    public InputStream getInputStream() throws IOException {
        InputStream is = this.classLoader.getResourceAsStream( this.path );
        if (is == null) {
            throw new FileNotFoundException(path+" cannot be opened");
        }
        return is;
    }

    public String getDescription() {
        return this.path;
    }

}
