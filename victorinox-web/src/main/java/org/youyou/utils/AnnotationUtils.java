package org.youyou.utils;

import org.youyou.annotation.RequestMapping;

import java.lang.annotation.Annotation;

/**
 * TODO:add description of class here, then delete the line.
 *
 * @author youpengfeiinfo
 * @version 13-7-22
 */
public class AnnotationUtils {

    public  static String getActionName(Annotation[] annotations){
        for (Annotation annotation : annotations) {
            if (annotation instanceof RequestMapping) {
               return  ((RequestMapping) annotation).value()[0];
            }
        }
        return "";
    }
}
