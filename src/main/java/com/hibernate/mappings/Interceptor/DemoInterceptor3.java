package com.hibernate.mappings.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DemoInterceptor3 implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("DemoInterceptor3 preHandle()-> request "+request);
        System.out.println("DemoInterceptor3 preHandle()-> response "+response);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("DemoInterceptor3 postHandle()-> request "+request);
        System.out.println("DemoInterceptor3 postHandle()-> response "+response);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("DemoInterceptor3 afterCompletion()-> request "+request);
        System.out.println("DemoInterceptor3 afterCompletion()-> response "+response);
    }
}
