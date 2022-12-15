package io.toprate.worker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.toprate.worker.statics.Resource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultDto {
    private Resource resource;
    private String imageUrl;
    private String title;
    private String author;
    private String description;
    private Date publishedAt;
    private String resourceId;
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
                .resourceId(elsevierEntry.getResourceId())
                .build();
    }
    public static SearchResultDto fromEuropeana(EuropeanaItem europeanaItem) {
        if (europeanaItem == null) {
            return null;
        }
        return SearchResultDto.builder()
                .resource(Resource.EUROPEANA)
                .imageUrl(getFirst(europeanaItem.getImageUrl()))
                .title(getFirst(europeanaItem.getTitle()))
                .author(getFirst(europeanaItem.getAuthor()))
                .description(getFirst(europeanaItem.getDescription()))
                .publishedAt(europeanaItem.getPublishedAt())
                .resourceId(europeanaItem.getResourceId())
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
                .resourceId(springerItem.getIdentifier())
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
                .resourceId(europePMCResult.getId())
                .build();
    }
}
