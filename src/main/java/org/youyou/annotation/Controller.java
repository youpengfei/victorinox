package org.youyou.annotation;

import org.youyou.common.constant.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO:add description of class here, then delete the line.
 *
 * @author youpengfeiinfo
 * @version 13-7-3
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
    String value() default "";
}
