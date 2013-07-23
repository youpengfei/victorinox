package org.youyou.utils;

/**
 * TODO:add description of class here, then delete the line.
 *
 * @author youpengfeiinfo
 * @version 13-7-22
 */
public class StringUtils {
    public static String trim(String str) {
        if (str == null)
            return null;
        return str.trim();
    }

    public static boolean isNullOrEmpty(String str) {
        return getLength(str) == 0;
    }

    public static boolean isNullOrBlank(String str) {
        return getLength(trim(str)) == 0;
    }
    public static int getLength(String str) {
        return str == null ? 0 : str.length();
    }

    public static boolean hasText(String str){
        return !isNullOrBlank(str);
    }

}
