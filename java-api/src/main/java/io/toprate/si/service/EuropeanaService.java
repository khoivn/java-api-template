package io.toprate.si.service;

import io.toprate.si.message.KeywordMessage;
import io.toprate.si.dto.EuropeanaSearchResult;
import io.toprate.si.dto.SearchResultDto;
import io.toprate.si.models.Keyword;
import io.toprate.si.models.SearchResult;
import io.toprate.si.producer.MessageProducer;
import io.toprate.si.repository.KeywordRepo;
import io.toprate.si.repository.SearchResultRepo;
import io.toprate.si.statics.Resource;
import io.toprate.si.statics.SiExchange;
import io.toprate.si.statics.SiRoutingKey;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EuropeanaService {
    private KeywordRepo keywordRepo;

    private SearchResultRepo searchResultRepo;
    MessageProducer messageProducer;

    MongoTemplate mongoTemplate;

    public int start(int page, int size) {
        return (page) * size + 1;
    }

    private HttpBase http;

    public Page<SearchResultDto> getDataDB(String name, Pageable pageable) {
        String keyword = name.toLowerCase();

        Keyword key = keywordRepo.findByKeyword(keyword);
        if (key == null) {
            return getData(keyword, pageable);
        } else {
            ObjectId id = key.getId();
            Page<SearchResult> results = searchResultRepo.findByKeywordIdsAndResource(id, Resource.EUROPEANA, pageable);
            List<SearchResultDto> resultDtos = results.stream().map(SearchResultDto::fromSearchResult).collect(Collectors.toList());
            return new PageImpl<>(resultDtos, pageable, results.getTotalElements());
        }
    }

    public Page<SearchResultDto> getData(String name, Pageable pageable) {

        Map<String, Object> params = Map.of(
                "query", name,
                "rows", pageable.getPageSize(),
                "start", start(pageable.getPageNumber(), pageable.getPageSize())
        );
        EuropeanaSearchResult europeanaSearchResult = http.get(Resource.EUROPEANA, params, EuropeanaSearchResult.class);
        List<SearchResultDto> searchResultDtos = europeanaSearchResult.getItems().stream().map(SearchResultDto::fromEuropeana).collect(Collectors.toList());
        messageProducer.sendMessage(new KeywordMessage(name), SiExchange.PUBLICATION_SYNC, SiRoutingKey.EUROPEANA);
        return new PageImpl<>(searchResultDtos, pageable, europeanaSearchResult.getTotalResults());
    }
}
