package org.youyou;

import org.youyou.annotation.RequestMapping;
import org.youyou.common.bean.ProcessBody;
import org.youyou.utils.ClazzUtils;
import org.youyou.utils.MethodHandler;
import org.youyou.utils.ProcessHandler;

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
    public static final String REDIRECT_TAG = "redirect:";

    public static void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        final ProcessBody processBody = ProcessHandler.getProcessBody(uri);
        final String invoke = MethodHandler.handle(processBody,request);
        if (invoke.startsWith(REDIRECT_TAG)) {
            response.sendRedirect(invoke.substring(REDIRECT_TAG.length()));
        } else {
            request.getRequestDispatcher(JSP_PATH + invoke + ".jsp").forward(request, response);
        }
    }


}
