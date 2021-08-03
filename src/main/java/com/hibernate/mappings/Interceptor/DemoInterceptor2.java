package com.hibernate.mappings.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DemoInterceptor2 implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("DemoInterceptor2 preHandle()-> request "+request);
        System.out.println("DemoInterceptor2 preHandle()-> response "+response);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("DemoInterceptor2 postHandle()-> request "+request);
        System.out.println("DemoInterceptor2 postHandle()-> response "+response);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("DemoInterceptor2 afterCompletion()-> request "+request);
        System.out.println("DemoInterceptor2 afterCompletion()-> response "+response);
    }
}
