package com.danyarov.library.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;

/**
 * Locale interceptor to handle language switching
 */
public class LocaleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String lang = request.getParameter("lang");
        if (lang != null) {
            Locale locale = new Locale(lang);
            RequestContextUtils.getLocaleResolver(request).setLocale(request, response, locale);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            Locale locale = RequestContextUtils.getLocale(request);
            modelAndView.addObject("currentLocale", locale.getLanguage());
        }
    }
}
