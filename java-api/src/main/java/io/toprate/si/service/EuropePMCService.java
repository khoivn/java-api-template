package io.toprate.si.service;

import io.toprate.si.dto.SearchResultDto;
import io.toprate.si.dto.europepmc.EuropePMCSearchResult;
import io.toprate.si.message.KeywordMessage;
import io.toprate.si.models.Keyword;
import io.toprate.si.models.SearchResult;
import io.toprate.si.producer.MessageProducer;
import io.toprate.si.repository.KeywordRepo;
import io.toprate.si.repository.SearchResultRepo;
import io.toprate.si.statics.Resource;
import io.toprate.si.statics.SiExchange;
import io.toprate.si.statics.SiRoutingKey;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class EuropePMCService {
    @Autowired
    private HttpBase http;

    @Autowired
    private KeywordRepo keywordRepo;

    @Autowired
    private SearchResultRepo searchResultRepo;

    @Autowired
    private MessageProducer messageProducer;

    public Page<SearchResultDto> fromDatabase(String query, Pageable pageable){
        Keyword keyword = keywordRepo.findByKeyword(query.toLowerCase());

        if (keyword == null) {
            return getEuropePMCData(query.toLowerCase(), pageable);
        } else {
            ObjectId id = keyword.getId();
            Page<SearchResult> resultPage = searchResultRepo.findByKeywordIdsAndResource(id, Resource.EUROPEPMC, pageable);
            List<SearchResultDto> searchResults = resultPage.stream().map(SearchResultDto::fromSearchResult).collect(Collectors.toList());
            return new PageImpl<>(searchResults, pageable, resultPage.getTotalElements());
        }
    }

    public Page<SearchResultDto> getEuropePMCData(String query, Pageable pageable) {
        Map<String, Object> params = Map.of(
                "query", query,
                "pageSize", pageable.getPageSize()
        );

        EuropePMCSearchResult europePMCSearchResult = http.get(Resource.EUROPEPMC, params, EuropePMCSearchResult.class);
        List<SearchResultDto> searchResults = europePMCSearchResult.getResultList().getResult().stream().map(SearchResultDto::fromEuropePMC).collect(Collectors.toList());
        Long totalResults = europePMCSearchResult.getHitCount();
        messageProducer.sendMessage(new KeywordMessage(query), SiExchange.PUBLICATION_SYNC, SiRoutingKey.EUROPEPMC);
        return new PageImpl<>(searchResults, pageable, totalResults);
    }
}