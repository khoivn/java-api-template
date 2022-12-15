package io.toprate.worker.service;

import io.toprate.worker.dto.EuropePMCSearchResult;
import io.toprate.worker.dto.EuropeanaSearchResult;
import io.toprate.worker.dto.SearchResultDto;
import io.toprate.worker.models.Keyword;
import io.toprate.worker.models.SearchResult;
import io.toprate.worker.repository.SearchResultRepo;
import io.toprate.worker.statics.Resource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EuropePMCService {
    private SearchResultBaseService searchResultService;

    private HttpBase http;

    public Page<SearchResultDto> getEuropePMCData(String query, Pageable pageable, Keyword savedKeyword){
        Map<String, Object> params = Map.of(
                "query", query,
                "pageSize", pageable.getPageSize());

        EuropePMCSearchResult europePMCSearchResult = http.get(Resource.EUROPEPMC, params, EuropePMCSearchResult.class);
        List<SearchResultDto> searchResultDtos = europePMCSearchResult.getResultList().getResult().stream().map(SearchResultDto::fromEuropePMC).collect(Collectors.toList());
        List<SearchResult> searchResults = searchResultDtos.stream().map(s -> SearchResult.fromSearchResultDto(s).withKeywordIds(List.of(savedKeyword.getId()))).collect(Collectors.toList());
        searchResultService.saveSearchResult(searchResults,savedKeyword);
        return new PageImpl<>(searchResultDtos, pageable, europePMCSearchResult.getHitCount());
    }

}
