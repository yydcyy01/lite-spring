package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.propertyeditors.CustomNumberEditor;

import java.io.InputStream;

/**
 *
 * 确保开始异常.
 *
 * 实现代码功能
 *
 * 跑通代码, 无异常
 *
 */


public class CustomNumberEditorTest {

    @Test
    public void testConvertString(){

        // 类作用 : 把别的类型转换为 Integer
        CustomNumberEditor editor = new CustomNumberEditor(Integer.class, true);

        // 想象成 拖拉拽的那种GUI beans 中的 button 按钮.

        //区域内设置值为 3
        editor.setAsText("3");
        // 取出这个 "东西" [不知道类型]
        Object value = editor.getValue();
        // 判断是不是 Integer 类型
        Assert.assertTrue(value instanceof Integer);
        // 类型转换成为 Integer , 哇, 是整形 3 耶
        Assert.assertEquals(3,((Integer)editor.getValue()).intValue());

        //清空
        editor.setAsText("");
        Assert.assertTrue(editor.getValue() == null);

        // 测试 希望跑出非法参数异常异常
        try {
            editor.setAsText("3.1");
        } catch (IllegalArgumentException e) {
            //return;
        }
        Assert.fail();

    }
}
