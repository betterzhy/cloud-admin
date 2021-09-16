package com.zhy.swagger;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;


@ConfigurationProperties(prefix = "open-api.swagger")
@EnableConfigurationProperties
@Component
@Primary
public class SwaggerProvider implements SwaggerResourcesProvider {

    @Setter
    @Getter
    private List<String> resources;

    public static final String API_URI = "/v3/api-docs";

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> swaggerResources = new ArrayList<>();
        resources.forEach(s -> {
            String[] split = s.split(",");
            String name = split[0];
            String url = split[1];
            String group = split[2];
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setName(name);
            if (Docket.DEFAULT_GROUP_NAME.equals(group)) {
                swaggerResource.setUrl(url + API_URI);
            } else {
                swaggerResource.setUrl(url + API_URI + "?group=" + group);
            }
            swaggerResource.setSwaggerVersion("3.0");
            swaggerResources.add(swaggerResource);
        });
        return swaggerResources;
    }
}