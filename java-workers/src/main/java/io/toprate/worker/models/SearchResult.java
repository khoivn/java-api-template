package io.toprate.worker.models;

import io.toprate.worker.dto.SearchResultDto;
import io.toprate.worker.statics.Resource;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("search-result")
@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class SearchResult extends BaseModel{
    private Resource resource;
    private String imageUrl;
    private String title;
    private String author;
    private String description;
    private Date publishedAt;
    private List<ObjectId> keywordIds;
    private String resourceId;


    @SneakyThrows
    public static SearchResult fromSearchResultDto(SearchResultDto searchResultDto) {
        if (searchResultDto == null) {
            return null;
        }
        return SearchResult.builder()
                .resource(searchResultDto.getResource())
                .imageUrl(searchResultDto.getImageUrl())
                .title(searchResultDto.getTitle())
                .author(searchResultDto.getAuthor())
                .description(searchResultDto.getDescription())
                .publishedAt(searchResultDto.getPublishedAt())
                .resourceId(searchResultDto.getResourceId())
                .build();
    }
}
