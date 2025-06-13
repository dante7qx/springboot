package org.dante.springboot.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI globalOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                    .title("Spirit API 接口文档")
                    .description("用于展示 Spirit 平台的 API 文档")
                    .version("v3.4.5")
                    .contact(new Contact()
                            .name("但丁")
                            .email("dev@example.com")
                            .url("https://dante7qx.github.io/")
                    )
                    .license(new License().name("Apache 2.0"))
                );
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("03-admin")  // 分组名，对应访问路径 /v3/api-docs/admin
                .displayName("管理API")
                .pathsToMatch("/admin/**")
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("02-user")   // /v3/api-docs/user
                .displayName("用户API")
                .pathsToMatch("/user/**")
                .build();
    }

    @Bean
    public GroupedOpenApi openApi() {
        return GroupedOpenApi.builder()
                .group("01-public")
                .displayName("公开API")
                .pathsToMatch("/public/**")
                .build();
    }

}
