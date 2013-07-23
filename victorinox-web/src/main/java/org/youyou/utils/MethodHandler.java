package org.youyou.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.youyou.common.bean.ProcessBody;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * TODO:add description of class here, then delete the line.
 *
 * @author youpengfeiinfo
 * @version 13-7-22
 */
public class MethodHandler {
    public static String handle(ProcessBody processBody) {
        try {
            final Method method = processBody.getMethod();


            return (String) method.invoke(processBody.getInstance());
        } catch (Exception ignore) {
            return null;
        }

    }
}
