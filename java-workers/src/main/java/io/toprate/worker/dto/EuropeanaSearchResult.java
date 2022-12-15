package io.toprate.worker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EuropeanaSearchResult {
    @JsonProperty("items")
    private List<EuropeanaItem> items;
    @JsonProperty("totalResults")
    private Integer totalResults;
    @JsonProperty("itemsCount")
    private Integer itemsCount;
}
