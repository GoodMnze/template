package com.mnze.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Locale;

@SpringBootApplication
public class MySpringApplication {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(MySpringApplication.class, args);

        // 1. beanFactory
        MySpringApplication bean = context.getBean(MySpringApplication.class);
        System.out.println(bean);
        // 2. applicationContext
        // 2.1 MessageSource 国际化消息
        String hi = context.getMessage("hi",null,Locale.getDefault());
        String hiZH = context.getMessage("hi",null, Locale.CHINA);
        System.out.println(hi);
        System.out.println(hiZH);

        // 2.2 ResourcePatternResolver 资源解析
        Resource[] resources = context.getResources("classpath:*");
        for (Resource resource : resources) {
            System.out.println(resource);
        }

        // 2.3 ApplicationEventPublisher 环境变量
        String port = context.getEnvironment().getProperty("server.port");
        System.out.println(port);

        // 2.4 Event
        context.publishEvent("hello");
    }

    @EventListener
    public void listener(String msg){
        System.out.println(msg);
    }
}
