package io.toprate.si.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.toprate.si.dto.europepmc.EuropePMCResult;
import io.toprate.si.dto.springer.SpringerItem;
import io.toprate.si.models.SearchResult;
import io.toprate.si.statics.Resource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultDto implements Serializable {
    private Resource resource;
    private String imageUrl;
    private String title;
    private String author;
    private String description;
    private Date publishedAt;
    public static SearchResultDto fromElsevier(ElsevierEntry elsevierEntry) {
        if (elsevierEntry == null) {
            return null;
        }
        return SearchResultDto.builder()
                .resource(Resource.ELSEVIER)
                .imageUrl(elsevierEntry.getImageUrl())
                .title(elsevierEntry.getTitle())
                .author(elsevierEntry.getCreator())
                .description(elsevierEntry.getPublicationName())
                .publishedAt(elsevierEntry.getCoverDate())
                .build();
    }
    public static SearchResultDto fromEuropeana(EuropeanaItem europeanaItem) {
        if (europeanaItem == null) {
            return null;
        }
        return SearchResultDto.builder()
                .resource(Resource.EUROPEANA)
                .imageUrl(getFirst(europeanaItem.getEdmPreview()))
                .title(getFirst(europeanaItem.getTitle()))
                .author(getFirst(europeanaItem.getDataProvider()))
                .description(getFirst(europeanaItem.getDcDescription()))
                .publishedAt(europeanaItem.getTimestamp_created())
                .build();
    }

    public static SearchResultDto convertToSearchResult(SpringerItem springerItem) {
        if (springerItem == null) {
            return null;
        }
        return SearchResultDto.builder()
                .title(springerItem.getTitle())
                .author(springerItem.getPublisher())
                .description(springerItem.getPublicationName())
                .publishedAt(springerItem.getPublicationDate())
                .imageUrl("")
                .resource(Resource.SPRINGER)
                .build();
    }

    public static <T> T getFirst(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public static SearchResultDto fromEuropePMC(EuropePMCResult europePMCResult) {
        if (europePMCResult == null) {
            return null;
        }
        return SearchResultDto.builder()
                .resource(Resource.EUROPEPMC)
                .imageUrl(europePMCResult.getImageUrl())
                .title(europePMCResult.getTitle())
                .author(europePMCResult.getAuthorString())
                .description(europePMCResult.getJournalTitle())
                .publishedAt(europePMCResult.getFirstPublicationDate())
                .build();
    }

    public static SearchResultDto fromSearchResult(SearchResult searchResult) {
        if (searchResult == null) {
            return null;
        }
        return SearchResultDto.builder()
                .resource(searchResult.getResource())
                .imageUrl(searchResult.getImageUrl())
                .title(searchResult.getTitle())
                .author(searchResult.getAuthor())
                .description(searchResult.getDescription())
                .publishedAt(searchResult.getPublishedAt())
                .build();
    }
}
