package io.toprate.worker.service;

import io.toprate.worker.dto.EuropeanaSearchResult;
import io.toprate.worker.dto.SearchResultDto;
import io.toprate.worker.models.Keyword;
import io.toprate.worker.models.SearchResult;
import io.toprate.worker.repository.KeywordRepo;
import io.toprate.worker.repository.SearchResultRepo;
import io.toprate.worker.statics.Resource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EuropeanaService {
    private HttpBase http;
    private SearchResultBaseService baseService;

    public int start(int page, int size) {
        return (page) * size + 1;
    }
    public Page<SearchResultDto> getData(String name, Pageable pageable, Keyword savedKeyword){
        Map<String, Object> params = Map.of(
                "query", name,
                "rows", pageable.getPageSize(),
                "start", start(pageable.getPageNumber(), pageable.getPageSize())
        );
        EuropeanaSearchResult europeanaSearchResult = http.get(Resource.EUROPEANA, params, EuropeanaSearchResult.class);
        List<SearchResultDto> searchResultDtos = europeanaSearchResult.getItems().stream().map(SearchResultDto::fromEuropeana).collect(Collectors.toList());
        List<SearchResult> searchResults = searchResultDtos.stream().map(s -> SearchResult.fromSearchResultDto(s).withKeywordIds(List.of(savedKeyword.getId()))).collect(Collectors.toList());
        baseService.saveSearchResult(searchResults,savedKeyword);
        return new PageImpl<>(searchResultDtos, pageable, europeanaSearchResult.getTotalResults());
    }

}
