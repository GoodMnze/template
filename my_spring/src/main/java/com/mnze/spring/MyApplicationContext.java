package com.mnze.spring;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiaolingfeng
 * @date 2023/4/2 6:32 PM
 */
public class MyApplicationContext {

    public static void main(String[] args) {
//          ClassPathXmlApplacationContext 基于 classpath 下 xml 格式的配置文件创建
//          FileSystemXmlApplicationContext 基于磁盘路径下的配置文件来创建
//          AnotationConfigApplicationContext 基于 java 配置类来创建
//          AnnotationConfigServletWebApplicationContext 基于 java 配置类来创建 用于 web 环境
        AnnotationConfigServletWebApplicationContext
                context = new AnnotationConfigServletWebApplicationContext(WebConfig.class);
    }

    @Configuration
    static class WebConfig{

        // 容器
        @Bean
        public ServletWebServerFactory servletWebServerFactory(){
            return new TomcatServletWebServerFactory();
        }

        // 服务分发器
        @Bean
        public DispatcherServlet dispatcherServlet(){
            return new DispatcherServlet();
        }

        // 分发器注册 -> 容器
        @Bean
        public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet){
            return new DispatcherServletRegistrationBean(dispatcherServlet,"/");
        }

        // 控制器
        @Bean("/")
        public Controller controller1(){
//            return new Controller() {
//                @Override
//                public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
//                    response.getWriter().println("hello");
//                    return null;
//                }
//            };

            return ((request, response) -> {
                response.getWriter().print("hello");
                return null;
            });
        }
    }
}
