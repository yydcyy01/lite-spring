package org.litespring.beans.propertyeditors;

import org.litespring.util.NumberUtils;
import org.litespring.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;

/**
 * @author YYDCYY
 * @create 2019-12-12
 */

public class CustomNumberEditor extends PropertyEditorSupport {

    private final Class<? extends Number> numberClass;

    private final NumberFormat numberFormat;

    private final boolean allowEmpty;

    public CustomNumberEditor(Class<? extends Number> numberClass,boolean allowEmpty) {
        this(numberClass,null,allowEmpty);
    }

    //构造函数初始化
    public CustomNumberEditor(Class<? extends Number> numberClass,
                              NumberFormat numberFormat, boolean allowEmpty) {

        if (numberClass == null || !Number.class.isAssignableFrom(numberClass)) {
            throw new IllegalArgumentException("Property class must be a subclass of Number");
        }
        this.numberClass = numberClass;
        this.allowEmpty = allowEmpty;
        this.numberFormat = numberFormat;
    }


    /**
     * 判断是非为空
     * 设置值
     * 解析
     * @param text
     * @throws IllegalArgumentException
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        //若为空,判断text 有无内容. 没有直接为 null
        if (this.allowEmpty && !StringUtils.hasText(text)) {
            // Treat empty String as null value
            setValue(null);
        } else if (this.numberFormat!=null) {
            // Use given NumberFormat for parsing text. 可以传入一个字符串格式.
            setValue(NumberUtils.parseNumber(text, this.numberClass,this.numberFormat));
        } else {
            // Use default valueOf methods for parsing text. 解析文本
            setValue(NumberUtils.parseNumber(text, this.numberClass));
        }
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Number) {
            super.setValue(NumberUtils.convertNumberToTargetClass((Number) value,this.numberClass));
        }else {
            super.setValue(value);
        }
    }

    @Override
    public String getAsText() {
        Object value=getValue();
        if (value==null) {
            return "";
        }
        if (this.numberFormat!=null) {
            return this.numberFormat.format(value);
        }else {
            return value.toString();
        }
    }

}