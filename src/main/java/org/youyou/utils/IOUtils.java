package org.youyou.utils;

import java.io.*;

/**
 *
 * @author youpengfeiinfo
 * @version 13-6-14
 */
public class IOUtils {
    public static Reader getInputStreamReader(Class<?> clazz, String fileName) {
        return new InputStreamReader(clazz.getResourceAsStream("/file/" + fileName));
    }


}
