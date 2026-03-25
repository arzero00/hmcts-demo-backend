package uk.gov.hmcts.reform.dev.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI taskApi() {

        return new OpenAPI()
            .info(new Info()
                      .title("Task Management API")
                      .version("1.0")
                      .description("Task service documentation"));
    }
}
