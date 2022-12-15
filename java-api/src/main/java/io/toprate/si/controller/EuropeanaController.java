package io.toprate.si.controller;

import io.toprate.si.dto.SearchResultDto;
import io.toprate.si.models.SearchResult;
import io.toprate.si.service.EuropeanaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@AllArgsConstructor
public class EuropeanaController {
    @Autowired
    EuropeanaService europeanaService;

    @GetMapping("/publication/europeana")
    @ResponseBody
    public ResponseEntity<Page<SearchResultDto>> searchByName(
            @RequestParam(value = "keyword", defaultValue = "") String name,
            Pageable pageable
    ) throws IOException {
        return ResponseEntity.ok(europeanaService.getDataDB(name, pageable));
    }
}
