package com.nova.mcart.controller;

import com.nova.mcart.dto.request.ProductSearchRequest;
import com.nova.mcart.dto.response.AutocompleteItemResponse;
import com.nova.mcart.dto.response.ProductSearchResponse;
import com.nova.mcart.service.ProductSearchGatewayService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search/products")
public class ProductSearchController {

    private final ProductSearchGatewayService productSearchGatewayService;

    @PostMapping
    public ProductSearchResponse search(@RequestBody ProductSearchRequest request) {
        return productSearchGatewayService.searchProducts(request);
    }

    @GetMapping("/autocomplete")
    public List<AutocompleteItemResponse> autocomplete(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "10") int size
    ) {

        if (q == null || q.isBlank()) {
            return List.of();
        }

        return productSearchGatewayService.autocomplete(q, size);
    }
}
