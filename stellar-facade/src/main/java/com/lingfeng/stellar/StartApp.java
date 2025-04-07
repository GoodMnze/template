package com.lingfeng.stellar;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lingfeng_stellar
 */

@Slf4j
@SpringBootApplication
public class StartApp {
    public static void main(String[] args) {
        SpringApplication.run(StartApp.class, args);
        log.info("服务启动成功");
    }
}
