package io.toprate.worker.feign;

import io.toprate.worker.dto.ElsevierInformation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "elsevier-api" , url = "${elsevier.url}")
public interface ElsevierFeign {
    @GetMapping("/scopus")
    public ElsevierInformation getAll(
            @RequestParam String query,
            @RequestParam String apiKey,
            @RequestParam Integer start,
            @RequestParam Integer count,
            @RequestParam String httpAccept
            );
}
