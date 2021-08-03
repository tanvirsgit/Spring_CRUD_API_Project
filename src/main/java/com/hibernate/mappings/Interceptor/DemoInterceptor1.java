package com.hibernate.mappings.Interceptor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DemoInterceptor1 implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("DemoInterceptor preHandle()-> request "+request.getRemoteUser());
        System.out.println("DemoInterceptor preHandle()-> response "+response);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("DemoInterceptor postHandle()-> request "+request);
        System.out.println("DemoInterceptor postHandle()-> response "+response);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("DemoInterceptor afterCompletion()-> request "+ request);
        System.out.println("DemoInterceptor afterCompletion()-> response "+response);
    }
}
