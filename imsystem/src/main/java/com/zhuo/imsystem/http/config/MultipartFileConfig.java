package com.zhuo.imsystem.http.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
public class MultipartFileConfig {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize(DataSize.ofKilobytes(2048));
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.ofKilobytes(2048));
        return factory.createMultipartConfig();
    }
}
