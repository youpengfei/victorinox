package org.youyou.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.youyou.common.bean.ProcessBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Map;

/**
 * TODO:add description of class here, then delete the line.
 *
 * @author youpengfeiinfo
 * @version 13-7-22
 */
public class MethodHandler {
    public static String handle(ProcessBody processBody, HttpServletRequest request) {
        try {
            final Method method = processBody.getMethod();
            final Class<?>[] parameterTypes = method.getParameterTypes();
            final List<String> paramNames = MethodParamNamesScanner.getParamNames(method);
            String args = null;
            int i = 0;
            for (Class<?> parameterType : parameterTypes) {
                Object o = parameterType.newInstance();
                final Map<String, Object> parameterMap = request.getParameterMap();
                for (String key : parameterMap.keySet()) {
                    final boolean contains = paramNames.contains(key);
                    if (contains) {
                        o = parameterMap.get(key);
                        break;
                    } else {
                        BeanUtils.setProperty(o, key, parameterMap.get(key));
                    }
                }
                args = o.toString();
                i++;
            }
            return (String) method.invoke(processBody.getInstance(), args);
        } catch (Exception ignore) {
            ignore.printStackTrace();
            return null;
        }

    }
}
