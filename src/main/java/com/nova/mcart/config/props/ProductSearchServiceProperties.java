package com.nova.mcart.config.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "mcart.services.product-search")
public class ProductSearchServiceProperties {
    private String baseUrl = "http://localhost:8081";
}
