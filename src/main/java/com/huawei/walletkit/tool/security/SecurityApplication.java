/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2020-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * SecurityApplication
 *
 * @author lWX883636
 * @since 2020-07-15
 */
@SpringBootApplication
public class SecurityApplication extends SpringBootServletInitializer{
    /*public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }*/

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SecurityApplication.class);
    }
}
