package io.toprate.worker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ElsevierSearchResult {
    @JsonProperty("entry")
    private List<ElsevierEntry> elsevierEntry;

    @JsonProperty("opensearch:totalResults")
    private Long totalResults;

    public List<ElsevierEntry> getElsevierEntry() {
        return elsevierEntry;
    }
}
