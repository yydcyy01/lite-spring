package org.litespring.beans.factory.config;

/**
 * @author YYDCYY
 * @create 2019-12-11
 */
public class TypedStringValue {

    private String value;
    public TypedStringValue(String value) {
        this.value=value;
    }
    public String getValue() {
        return this.value;
    }
}