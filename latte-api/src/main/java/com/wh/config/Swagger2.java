package com.wh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: latte
 * @description:
 * @author: wh
 * @create: 2020-01-20 11:05
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    // http://localhost:8080/swagger-ui.html
    // http://localhost:8080/doc.html

    //配置swagger2核心配置 docket
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)//指定api类型为swagger2
                .apiInfo(apiInfo())                 //用于定义api文档的汇总信息
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.wh.controller"))//扫描controller
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("王豪的学习项目")    //文档页标题
                .contact(new Contact("王豪"
                        , "null"
                        , "419264755@qq.com"))//联系人信息
                .description("api文档")//详细信息
                .version("0.0.1")//版本号
                .termsOfServiceUrl("null")//网站地址
                .build();

    }
}
