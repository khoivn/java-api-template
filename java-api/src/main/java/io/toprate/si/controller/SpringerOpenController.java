package io.toprate.si.controller;

import io.toprate.si.service.SpringerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SpringerOpenController {

    @Autowired
    private SpringerService service;

    @RequestMapping(value = "/publication/springer", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> springer(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            Pageable pageable
    ) {
        return ResponseEntity.ok().body(this.service.getData(keyword,pageable));
    }

}
