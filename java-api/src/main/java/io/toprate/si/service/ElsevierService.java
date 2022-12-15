package io.toprate.si.service;

import io.toprate.si.dto.ElsevierInformation;
import io.toprate.si.dto.SearchResultDto;
import io.toprate.si.feign.ElsevierFeign;
import io.toprate.si.models.Keyword;
import io.toprate.si.models.SearchResult;
import io.toprate.si.hashKey.HashKey;
import io.toprate.si.producer.MessageProducer;
import io.toprate.si.repository.KeywordRepo;
import io.toprate.si.repository.SearchResultRepo;
import io.toprate.si.statics.Resource;
import io.toprate.si.statics.SiExchange;
import io.toprate.si.statics.SiRoutingKey;
import io.toprate.si.utils.KeywordUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElsevierService {

    private static final String HTTP_ACCEPT = "application/json";
    @Autowired
    private ElsevierFeign elsevierFeign;
    @Autowired
    private KeywordRepo keywordRepo;
    @Autowired
    private SearchResultRepo searchResultRepo;
    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private RedisBase redis;

    @Value("${elsevier.apiKey}")
    private String apiKey;//Dua vao properties

    public Integer start(Integer page, Integer count) {
        return (page - 1) * count;
    }

    public Page<SearchResultDto> getAllInfo(String query, Pageable pageable) {
        String formattedKeyword = KeywordUtils.formatInputKeyword(query);
        HashKey hashKey = new HashKey(pageable.getPageNumber(), pageable.getPageSize(), Resource.ELSEVIER);
        List<SearchResultDto> resultDtos = redis.getFromRedis(query, hashKey);
        if (!CollectionUtils.isEmpty(resultDtos)) {
            return new PageImpl<>(resultDtos, pageable, resultDtos.size());
        }
        Keyword keyword = keywordRepo.findByKeyword(formattedKeyword);
        if (keyword == null) {
            Integer start = start(pageable.getPageNumber(), pageable.getPageSize());
            ElsevierInformation info = elsevierFeign.getAll(formattedKeyword, apiKey, start, pageable.getPageSize(), MediaType.APPLICATION_JSON_VALUE);
            List<SearchResultDto> searchResultDtos = info.getResults().getElsevierEntry().stream().map(SearchResultDto::fromElsevier).collect(Collectors.toList());
            redis.saveToRedis(query, searchResultDtos, hashKey);
            Long totalResults = info.getResults().getTotalResults();
            messageProducer.sendMessage(new Keyword(formattedKeyword), SiExchange.PUBLICATION_SYNC, SiRoutingKey.ELSEVIER);
            return new PageImpl<>(searchResultDtos, pageable, totalResults);
        }
        ObjectId id = keyword.getId();
        Page<SearchResult> results = searchResultRepo.findByKeywordIdsAndResource(id, Resource.ELSEVIER, pageable);
        resultDtos = results.stream().map(SearchResultDto::fromSearchResult).collect(Collectors.toList());
        redis.saveToRedis(query, resultDtos, hashKey);
        return new PageImpl<>(resultDtos, pageable, resultDtos.size());
    }

}
