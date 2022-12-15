package io.toprate.si.service;

import io.toprate.si.dto.SearchResultDto;
import io.toprate.si.dto.springer.SpringerSearchResult;
import io.toprate.si.message.KeywordMessage;
import io.toprate.si.models.Keyword;
import io.toprate.si.models.SearchResult;
import io.toprate.si.producer.MessageProducer;
import io.toprate.si.repository.KeywordRepo;
import io.toprate.si.repository.SearchResultRepo;
import io.toprate.si.statics.Resource;
import io.toprate.si.statics.SiExchange;
import io.toprate.si.statics.SiRoutingKey;
import io.toprate.si.utils.KeywordUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SpringerService {


    private static final int PAGE_DEFAULT = 1;
    @Autowired
    private HttpBase http;

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private KeywordRepo keywordRepo;

    @Autowired
    private SearchResultRepo searchResultRepo;

    public int pageStart(int page, int size) {
        return (page) * size + PAGE_DEFAULT;
    }

    public Page<SearchResultDto> getDataSendWorker(String keyword, Pageable pageable) {
        Map<String, Object> params = Map.of(
                "q", "keyword:" + keyword,
                "p", pageable.getPageSize(),
                "s", this.pageStart(pageable.getPageNumber(), pageable.getPageSize())
        );
        SpringerSearchResult result = http.get(Resource.SPRINGER, params, SpringerSearchResult.class);
        List<SearchResultDto> lstSpringer = result.getRecords().stream().map(SearchResultDto::convertToSearchResult).collect(Collectors.toList());
        this.messageProducer.sendMessage(new KeywordMessage(keyword), SiExchange.PUBLICATION_SYNC, SiRoutingKey.SPRINGER);
        return new PageImpl<>(lstSpringer, pageable, result.getResult().get(0).getTotal());
    }

    public Page<SearchResultDto> getData(String keyword, Pageable pageable) {
        String formattedKeyword = KeywordUtils.formatInputKeyword(keyword);
        Keyword keywordInDb = this.keywordRepo.findByKeyword(formattedKeyword);
        if (Objects.isNull(keywordInDb)) {
            return this.getDataSendWorker(formattedKeyword, pageable);
        }
        ObjectId objectId = keywordInDb.getId();
        Page<SearchResult> searchResults = this.searchResultRepo.findByKeywordIdsAndResource(objectId, Resource.SPRINGER, pageable);
        List<SearchResultDto> listSearchResultDto = searchResults.stream().map(SearchResultDto::fromSearchResult).collect(Collectors.toList());
        return new PageImpl<>(listSearchResultDto, pageable, searchResults.getTotalPages());
    }

}
