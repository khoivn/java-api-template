package io.toprate.worker.service;

import io.toprate.worker.models.Keyword;
import io.toprate.worker.models.SearchResult;
import io.toprate.worker.repository.SearchResultRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchResultBaseService {
    @Autowired
    private SearchResultRepo searchResultRepo;

    public void saveSearchResult(List<SearchResult> searchResults, Keyword savedKeyword){
        List<String> ids = searchResults.stream().map(SearchResult::getResourceId).collect(Collectors.toList());
        List<SearchResult> oldResultList = searchResultRepo.findByResourceIdIn(ids);
        List<String> idsDB = oldResultList.stream().map(SearchResult::getResourceId).collect(Collectors.toList());
        List<SearchResult> updateOldResultList = oldResultList.stream().peek(s->s.getKeywordIds().add(savedKeyword.getId())).collect(Collectors.toList());
        searchResultRepo.saveAll(updateOldResultList);
        List<SearchResult> newResultList = searchResults.stream().filter(s -> !idsDB.contains(s.getResourceId())).collect(Collectors.toList());
        searchResultRepo.saveAll(newResultList);
    }
}
