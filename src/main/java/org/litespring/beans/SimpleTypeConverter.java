package org.litespring.beans;

import com.sun.corba.se.impl.io.TypeMismatchException;

/**
 * @author YYDCYY
 * @create 2019-12-12
 */
public class SimpleTypeConverter implements TypeConverter {

    public <T> T convertIfNecessary(Object value, Class<T> requireType) throws TypeMismatchException {
        return null;
    }
}