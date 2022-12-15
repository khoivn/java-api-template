package io.toprate.si.controller;

import io.toprate.si.dto.SearchResultDto;
import io.toprate.si.feign.ElsevierFeign;
import io.toprate.si.service.ElsevierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableFeignClients
public class ElsevierController {

    @Autowired
    ElsevierService elsevierService;
    @Autowired
    ElsevierFeign elsevierRepository;


    @GetMapping("/publication/elsevier")
    public ResponseEntity<Page<SearchResultDto>> getAll(@RequestParam String keyword,
                                                        Pageable pageable) {
        return ResponseEntity.ok().body(elsevierService.getAllInfo(keyword, pageable));
    }

}
