package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.propertyeditors.CustomBooleanEditor;

/**
 * @author YYDCYY
 * @create 2019-12-12
 */
public class CustomBooleanEditorTest {

    @Test
    public void testConvertStringToBoolean(){
        // true : 运行为空值
        CustomBooleanEditor editor = new CustomBooleanEditor(true);

        editor.setAsText("true");
        Assert.assertEquals(true, ((Boolean)editor.getValue()));
        editor . setAsText ("true") ;
        Assert. assertEquals (true, ( (Boolean) editor .getValue() ) . booleanValue()) ;
        editor.setAsText("false") ;
        Assert. assertEquals(false, ( (Boolean) editor . getValue() ) . booleanValue()) ;
        editor . setAsText ("on") ;
        Assert. assertEquals (true, ( (Boolean) editor .getValue() ) . booleanValue()) ;
        editor. setAsText ("off") ;
        Assert. assertEquals(false, ( (Boolean) editor . getValue() ) . booleanValue()) ;
        editor. setAsText ("yes") ;
        Assert. assertEquals (true, ( (Boolean) editor .getValue() ) . booleanValue() ) ;
        editor. setAsText ("no") ;
        Assert. assertEquals (false, ( (Boolean) editor .getValue() ) .booleanValue ()) ;
        try {
            editor.setAsText("aabbcc");
        }catch (IllegalArgumentException e) {
            return;
        }
        Assert.fail();
    }
}
