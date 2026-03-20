package com.nova.mcart.controller;

import com.nova.mcart.dto.response.ReindexJobStatusResponse;
import com.nova.mcart.dto.response.ReindexStartResponse;
import com.nova.mcart.dto.response.SearchStatusResponse;
import com.nova.mcart.service.ProductSearchGatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {

    private final ProductSearchGatewayService searchGatewayService;

    @GetMapping("/es/reindex")
    public String reindexAll() {
        return searchGatewayService.reindexAll();
    }

    @GetMapping("/es/index")
    public String indexOne(@RequestParam Long id) {
        return searchGatewayService.indexOne(id);
    }

    @PostMapping("/bootstrap")
    public void bootstrap() {
        searchGatewayService.bootstrap();
    }

    @GetMapping("/status")
    public SearchStatusResponse status() {
        return searchGatewayService.status();
    }

    @PostMapping("/reindex")
    public ReindexStartResponse reindex() {
        return searchGatewayService.reindex();
    }

    @GetMapping("/reindex/status/{jobId}")
    public ReindexJobStatusResponse reindexStatus(@PathVariable String jobId) {
        return searchGatewayService.reindexStatus(jobId);
    }
}
