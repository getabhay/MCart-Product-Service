package com.nova.mcart.config;

import com.nova.mcart.config.props.ProductSearchServiceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(ProductSearchServiceProperties.class)
public class ProductSearchGatewayConfig {

    @Bean
    public RestClient productSearchRestClient(RestClient.Builder builder,
                                              ProductSearchServiceProperties properties) {
        return builder
                .baseUrl(properties.getBaseUrl())
                .build();
    }
}
