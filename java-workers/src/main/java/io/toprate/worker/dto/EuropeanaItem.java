package io.toprate.worker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EuropeanaItem {

    @JsonProperty("dataProvider")
    private List<String> author;
    @JsonProperty("edmPreview")
    private List<String> imageUrl;
    @JsonProperty("title")
    private List<String> title;
    @JsonProperty("dcDescription")
    private List<String> description;
    @JsonProperty("timestamp_created")
    private Date publishedAt;
    @JsonProperty("id")
    private String resourceId;
}
