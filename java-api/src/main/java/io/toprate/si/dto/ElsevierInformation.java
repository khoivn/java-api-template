package io.toprate.si.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ElsevierInformation {
    @JsonProperty("search-results")
    private ElsevierSearchResult results;

}
