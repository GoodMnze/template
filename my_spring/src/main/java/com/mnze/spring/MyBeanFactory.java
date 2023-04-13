package com.mnze.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author xiaolingfeng
 * @date 2023/4/2 3:36 PM
 */
public class MyBeanFactory {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 注册一个 bean
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
        beanFactory.registerBeanDefinition("config",beanDefinition);

        // 注册常用的后处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);

        // beanFactory 后处理器 补充bean的定义
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(beanFactoryPostProcessor -> {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });

        // bean 后处理器 -> 对 bean 生命周期各个阶段提供扩展 Autowired Resource
        beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(beanFactory::addBeanPostProcessor);

        // 打印 bean
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            System.out.println(beanName);
        }

        // 懒加载 bean
        beanFactory.preInstantiateSingletons();
        Bean2 bean2 = beanFactory.getBean(Bean1.class).getBean2();
        System.out.println(bean2);
    }

    @Configuration
    static class Config{

        @Bean
        public Bean1 bean1(){
            return new Bean1();
        }

        @Bean
        public Bean2 bean2(){
            return new Bean2();
        }
    }

    @Component
    @Slf4j
    static class Bean1{
        void bean1(){
            log.info(">>> bean1 初始化");
        }
        @Autowired
        private Bean2 bean2;

        public Bean2 getBean2(){
            return bean2;
        }
    }

    @Component
    static class Bean2{
    }
}
