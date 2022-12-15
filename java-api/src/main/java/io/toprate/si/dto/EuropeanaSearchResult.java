package io.toprate.si.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EuropeanaSearchResult {
    private List<EuropeanaItem> items;
    private Integer totalResults;
    private Integer itemsCount;
}
