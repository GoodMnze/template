package com.lingfeng.stellar;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = ServiceContent.class)
public class ServiceTestConfig {
}
