/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.config;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.MapUtils;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.*;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            //加了ApiOperation注解的类，才生成接口文档
            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
            //包下的类，才生成接口文档
            //.apis(RequestHandlerSelectors.basePackage("io.renren.controller"))
            .paths(PathSelectors.any())
            .build()
            .securitySchemes(security());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("人人开源")
            .description("renren-fast文档")
            .termsOfServiceUrl("https://www.renren.io")
            .version("3.0.0")
            .build();
    }

    private List<ApiKey> security() {
        return newArrayList(
            new ApiKey("token", "token", "header")
        );
    }

    @Bean
    public WebMvcEndpointHandlerMapping webMvcEndpointHandlerMapping(WebEndpointsSupplier wes
            , ServletEndpointsSupplier ses, ControllerEndpointsSupplier ces, EndpointMediaTypes emt
            , CorsEndpointProperties cep, WebEndpointProperties wep, Environment env) {
        List<ExposableEndpoint<?>> allEndpoints = new ArrayList<>();
        Collection<ExposableWebEndpoint> webEndpoints = wes.getEndpoints();
        allEndpoints.addAll(webEndpoints);
        allEndpoints.addAll(ses.getEndpoints());
        allEndpoints.addAll(ces.getEndpoints());
        String basePath = wep.getBasePath();
        EndpointMapping endpointMapping = new EndpointMapping(basePath);
        boolean shouldRegisterLinksMapping = shouldRegisterLinksMapping(wep, env, basePath);
        return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, emt
                , cep.toCorsConfiguration(), new EndpointLinksResolver(
                allEndpoints, basePath), shouldRegisterLinksMapping, null);
    }
    /**
     * shouldRegisterLinksMapping
     *
     * @param wep
     * @param env
     * @param basePath
     * @return
     */
    private boolean shouldRegisterLinksMapping(WebEndpointProperties wep, Environment env, String basePath) {
        return wep.getDiscovery().isEnabled() && (StringUtils.hasText(basePath) || ManagementPortType.get(env).equals(ManagementPortType.DIFFERENT));
    }

}