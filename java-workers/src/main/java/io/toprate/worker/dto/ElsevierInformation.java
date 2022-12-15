package io.toprate.worker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ElsevierInformation {
    @JsonProperty("search-results")
    private ElsevierSearchResult results;

}
