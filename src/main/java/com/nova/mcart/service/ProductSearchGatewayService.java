package com.nova.mcart.service;

import com.nova.mcart.dto.request.ProductSearchRequest;
import com.nova.mcart.dto.response.AutocompleteItemResponse;
import com.nova.mcart.dto.response.ProductSearchResponse;
import com.nova.mcart.dto.response.ProductSuggestResponse;
import com.nova.mcart.dto.response.ReindexJobStatusResponse;
import com.nova.mcart.dto.response.ReindexStartResponse;
import com.nova.mcart.dto.response.SearchStatusResponse;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ProductSearchGatewayService {

    private final RestClient restClient;

    public ProductSearchGatewayService(RestClient productSearchRestClient) {
        this.restClient = productSearchRestClient;
    }

    public ProductSearchResponse searchProducts(ProductSearchRequest request) {
        return restClient.post()
                .uri("/api/search/products")
                .body(request)
                .retrieve()
                .body(ProductSearchResponse.class);
    }

    public List<AutocompleteItemResponse> autocomplete(String q, int size) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/search/products/autocomplete")
                        .queryParam("q", q)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public ProductSuggestResponse suggest(String q) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/search/suggest").queryParam("q", q).build())
                .retrieve()
                .body(ProductSuggestResponse.class);
    }

    public String reindexAll() {
        return restClient.get()
                .uri("/api/search/es/reindex")
                .retrieve()
                .body(String.class);
    }

    public String indexOne(Long id) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/search/es/index").queryParam("id", id).build())
                .retrieve()
                .body(String.class);
    }

    public void bootstrap() {
        restClient.post()
                .uri("/api/search/bootstrap")
                .retrieve()
                .toBodilessEntity();
    }

    public SearchStatusResponse status() {
        return restClient.get()
                .uri("/api/search/status")
                .retrieve()
                .body(SearchStatusResponse.class);
    }

    public ReindexStartResponse reindex() {
        return restClient.post()
                .uri("/api/search/reindex")
                .retrieve()
                .body(ReindexStartResponse.class);
    }

    public ReindexJobStatusResponse reindexStatus(String jobId) {
        return restClient.get()
                .uri("/api/search/reindex/status/{jobId}", jobId)
                .retrieve()
                .body(ReindexJobStatusResponse.class);
    }
}
