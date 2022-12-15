package io.toprate.worker.service;

import io.toprate.worker.dto.SearchResultDto;
import io.toprate.worker.dto.SpringerSearchResult;
import io.toprate.worker.models.Keyword;
import io.toprate.worker.models.SearchResult;
import io.toprate.worker.repository.SearchResultRepo;
import io.toprate.worker.statics.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SpringerService {

    @Autowired
    private SearchResultRepo searchResultRepo;

    @Autowired
    private HttpBase http;

    private static final int PAGE_DEFAULT = 1;

    @Value("${springeropen.url}")
    private String url;

    public int pageStart(int page, int size) {
        return (page) * size + PAGE_DEFAULT;
    }

    public Page<SearchResultDto> getData(String keyword, Pageable pageable, Keyword keywordId) {
        Map<String, Object> params = Map.of(
                "q", "keyword: " + keyword,
                "p", pageable.getPageSize(),
                "s", this.pageStart(pageable.getPageNumber(), pageable.getPageSize())
        );
        SpringerSearchResult result = this.http.get(Resource.SPRINGER, params, SpringerSearchResult.class);
        List<SearchResultDto> lstSpringer = result.getRecords().stream().map(SearchResultDto::convertToSearchResult).collect(Collectors.toList());
        List<SearchResult> listSearchResults = lstSpringer.stream().map(springer -> SearchResult.fromSearchResultDto(springer).withKeywordIds(List.of(keywordId.getId()))).collect(Collectors.toList());
        this.searchResultRepo.saveAll(listSearchResults);
        return new PageImpl<>(lstSpringer, pageable, result.getResult().get(0).getTotal());
    }

}
