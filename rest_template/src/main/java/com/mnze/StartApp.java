package com.mnze;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author mnze
 */

@Slf4j
@SpringBootApplication
@ImportResource("classpath:/applicationContext.xml")
public class StartApp {
    public static void main(String[] args) {
        SpringApplication.run(StartApp.class, args);
        log.info("服务启动成功");
    }
}
