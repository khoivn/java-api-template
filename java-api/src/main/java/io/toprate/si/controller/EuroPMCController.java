package io.toprate.si.controller;

import io.toprate.si.dto.SearchResultDto;
import io.toprate.si.service.EuropePMCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EuroPMCController {
    @Autowired
    private EuropePMCService europePMCService;

    @GetMapping("/publication/europepmc")
    @ResponseBody
    public ResponseEntity<Page<SearchResultDto>> searchByKeyword(
            @RequestParam(value = "keyword", defaultValue = "") String keyword, Pageable pageable) {
        return ResponseEntity.ok(europePMCService.fromDatabase(keyword, pageable));
    }
}
