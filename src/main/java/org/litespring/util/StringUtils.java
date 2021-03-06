package org.litespring.util;

/**
 * @author YYDCYY
 * @create 2019-12-11
 */

public abstract class StringUtils {
    public static boolean hasLength(String str) {
        return hasLength((CharSequence)str);
    }

    public static boolean hasLength(CharSequence str) {
        return (str != null && str.length()>0);
    }

    public static boolean hasText(String str) {
        return hasText((CharSequence)str);
    }

    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        int strlen=str.length();
        for (int i = 0; i < strlen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    // 去除所有空格
    public static String trimAllWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb=new StringBuilder();
        int index=0;
        while(sb.length()>index) {
            if (Character.isWhitespace(sb.charAt(index))) {
                sb.deleteCharAt(index);
            }else {
                index++;
            }
        }
        return sb.toString();
       //TODO 先跑通 测试用例再实现功能
       //return null;
    }
}
