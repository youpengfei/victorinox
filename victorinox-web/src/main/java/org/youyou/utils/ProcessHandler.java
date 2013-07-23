package org.youyou.utils;

import org.youyou.annotation.RequestMapping;
import org.youyou.common.bean.ProcessBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * TODO:add description of class here, then delete the line.
 *
 * @author youpengfeiinfo
 * @version 13-7-22
 */
public class ProcessHandler {
    public static String CONTROLLER_PACKAGE = "org.youyou.controller";
    public static Map<String, ProcessBody> processMapping = new LinkedHashMap<String, ProcessBody>();
    final static Set<Class<?>> classSet = ClazzUtils.getClasses(CONTROLLER_PACKAGE);
    static {
        for (Class<?> clazz : classSet) {
            final Method[] methods = clazz.getMethods();
            final Annotation[] annotations = clazz.getAnnotations();
            String parentActionName = AnnotationUtils.getActionName(annotations);
            Object o = ClazzUtils.getNewInstance(clazz);
            for (Method method : methods) {
                String actionName = AnnotationUtils.getActionName(method.getAnnotations());
                if (StringUtils.hasText(actionName)) {
                    ProcessBody processBody = new ProcessBody();
                    processBody.setInstance(o);
                    processBody.setMethod(method);
                    processMapping.put(parentActionName + "/" + actionName, processBody);
                }
            }
        }
    }

    public static ProcessBody getProcessBody(String uri) {
        return processMapping.get(uri.substring(uri.indexOf("/") + 1));
    }
}
