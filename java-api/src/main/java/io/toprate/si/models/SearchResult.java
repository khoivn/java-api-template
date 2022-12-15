package io.toprate.si.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.toprate.si.dto.SearchResultDto;
import io.toprate.si.statics.Resource;
import lombok.*;
import org.apache.commons.beanutils.BeanUtils;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("search-result")
@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResult {
    private Resource resource;
    private String imageUrl;
    private String title;
    private String author;
    private String description;
    private Date publishedAt;
    private List<ObjectId> keywordIds;

    @SneakyThrows
    public static SearchResult fromSearchResultDto(SearchResultDto searchResultDto) {
        SearchResult searchResult = new SearchResult();
        BeanUtils.copyProperties(searchResult, searchResultDto);
        return searchResult;
    }
}
