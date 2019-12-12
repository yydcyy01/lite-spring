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
     *
     * 设置值
     *
     * 解析
     * @param text
     * @throws IllegalArgumentException
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && !StringUtils.hasText(text)) {
            setValue(null);
        } else if (this.numberFormat!=null) {
            setValue(NumberUtils.parseNumber(text, this.numberClass,this.numberFormat));
        } else {
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