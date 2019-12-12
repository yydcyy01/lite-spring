package org.litespring.beans;

import com.sun.corba.se.impl.io.TypeMismatchException;

/**
 * @author YYDCYY
 * @create 2019-12-12
 */
public interface TypeConverter {
    <T> T convertIfNecessary(Object value,Class<T> requireType) throws TypeMismatchException;
}