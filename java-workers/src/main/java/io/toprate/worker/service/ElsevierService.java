
package io.toprate.worker.service;

import io.toprate.worker.dto.ElsevierInformation;
import io.toprate.worker.dto.SearchResultDto;
import io.toprate.worker.feign.ElsevierFeign;
import io.toprate.worker.models.Keyword;
import io.toprate.worker.models.SearchResult;
import io.toprate.worker.repository.KeywordRepo;
import io.toprate.worker.repository.SearchResultRepo;

/*import io.toprate.si.dto.ElsevierInformation;
import io.toprate.si.dto.SearchResultDto;
import io.toprate.si.feign.ElsevierFeign;
import io.toprate.si.models.Keyword;
import io.toprate.si.models.SearchResult;
import io.toprate.si.repository.KeywordRepo;
import io.toprate.si.repository.SearchResultRepo;
import io.toprate.si.statics.Resource;*/

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ElsevierService {

    @Value("${elsevier.apiKey}")
    private String apiKey ;//Dua vao properties

    private static final String HTTP_ACCEPT = "application/json";

    @Autowired
    ElsevierFeign elsevierFeign;

    @Autowired
    KeywordRepo keywordRepo;
    @Autowired
    SearchResultRepo searchResultRepo;
    List <SearchResultDto> searchResultDtos;
    List <SearchResult> searchResults;
    public Integer start(Integer page, Integer count) {
        return (page - 1) * count;
    }


    public Page<SearchResultDto> getAllInfo(Keyword keyword, Pageable pageable) {
        Integer start = start(pageable.getPageNumber(), pageable.getPageSize());
        ElsevierInformation info = elsevierFeign.getAll(keyword.getKeyword(), apiKey, start, pageable.getPageSize(), MediaType.APPLICATION_JSON_VALUE);
        searchResultDtos = info.getResults().getElsevierEntry().stream().map(SearchResultDto::fromElsevier)
                .collect(Collectors.toList());
        Long totalResults = info.getResults().getTotalResults();
        searchResults = searchResultDtos.stream()
                .map(s -> SearchResult.fromSearchResultDto(s))
                .map(s -> s.withKeywordIds(Collections.singletonList(keyword.getId())))
                .collect(Collectors.toList());
        saveSearchResult(searchResults,keyword);
        //searchResultRepo.saveAll(searchResults);
        return new PageImpl<>(searchResultDtos, pageable, totalResults);
    }

    public void saveSearchResult(List<SearchResult> searchResults, Keyword keyword) {
        List<String> resourceIdSearchResults = searchResults.stream()
                .map(searchResult -> searchResult.getResourceId())
                .collect(Collectors.toList());
        List<SearchResult> searchResultsExisting = searchResultRepo.findByResourceIdIn(resourceIdSearchResults);
        List<String> resourceIdExisting = searchResultsExisting.stream().map(s -> s.getResourceId()).collect(Collectors.toList());
        List<SearchResult> updateSearchResults = searchResultsExisting.stream()
                .peek(searchResult -> searchResult.getKeywordIds().add(keyword.getId()))
                .collect(Collectors.toList());
        List<SearchResult> newSearchResults = searchResults.stream()
                .filter(s -> !resourceIdExisting.contains(s.getResourceId()))
                .collect(Collectors.toList());
        searchResultRepo.saveAll(updateSearchResults);
        searchResultRepo.saveAll(newSearchResults);
    }
}

