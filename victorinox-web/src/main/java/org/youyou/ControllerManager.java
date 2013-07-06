package org.youyou;

import org.youyou.annotation.RequestMapping;
import org.youyou.common.bean.ProcessBody;
import org.youyou.utils.ClazzUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * TODO:add description of class here, then delete the line.
 *
 * @author youpengfeiinfo
 * @version 13-6-16
 */
public class ControllerManager {
    public static String JSP_PATH = "/WEB-INF/jsp/";
    public static String CONTROLLER_PACKAGE = "org.youyou.controller";

    public static Map<String, ProcessBody> requestMapping = new LinkedHashMap<String, ProcessBody>();
    final static Set<Class<?>> classSet = ClazzUtils.getClasses(CONTROLLER_PACKAGE);

    static {
        for (Class<?> aClass : classSet) {
            final Method[] methods = aClass.getMethods();
            final Annotation[] annotations = aClass.getAnnotations();
            String parentActionName = "";
            for (Annotation annotation : annotations) {
                if (annotation instanceof RequestMapping) {
                    parentActionName = ((RequestMapping) annotation).value()[0];
                }
            }
            Object o = null;
            try {
                o = aClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            for (Method method : methods) {
                Boolean isAction = false;
                String actionName = "";
                for (Annotation annotation : method.getAnnotations()) {
                    if (annotation instanceof RequestMapping) {
                        isAction = true;
                        actionName = ((RequestMapping) annotation).value()[0];
                    }
                }
                if (isAction) {
                    ProcessBody processBody = new ProcessBody();
                    processBody.setInstance(o);
                    processBody.setMethod(method);
                    requestMapping.put(parentActionName + "/" + actionName, processBody);
                }

            }
            System.out.println("==================");
        }
    }

    public static void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        final ProcessBody processBody = requestMapping.get(uri.substring(uri.indexOf("/") + 1));
        try {
            final String invoke = (String) processBody.getMethod().invoke(processBody.getInstance());
            request.getRequestDispatcher(JSP_PATH + invoke + ".jsp").forward(request, response);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
