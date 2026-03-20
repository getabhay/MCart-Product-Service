package com.nova.mcart.controller;

import com.nova.mcart.dto.response.ProductSuggestResponse;
import com.nova.mcart.service.ProductSearchGatewayService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
public class ProductSuggestController {

    private final ProductSearchGatewayService service;

    public ProductSuggestController(ProductSearchGatewayService service) {
        this.service = service;
    }

    @GetMapping("/suggest")
    public ProductSuggestResponse suggest(@RequestParam(value = "q", required = false) String q) {
        if (q == null || q.isBlank()) {
            ProductSuggestResponse out = new ProductSuggestResponse();
            out.setQ(q);
            out.setCorrectedQuery(null);
            out.setDidYouMean(java.util.List.of());
            out.setUsedQuery(q);
            out.setProducts(java.util.List.of());
            out.setBrands(java.util.List.of());
            out.setCategories(java.util.List.of());
            return out;
        }
        return service.suggest(q);
    }
}
