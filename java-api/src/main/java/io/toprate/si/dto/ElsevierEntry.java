package io.toprate.si.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "title",
        "creator",
        "publicationName",
        "CoverDisplayDate"
})
public class ElsevierEntry {
    @JsonProperty("imageUrl")
    private String imageUrl;
    @JsonProperty("dc:title")
    private String title;
    @JsonProperty("dc:creator")
    private String creator;
    @JsonProperty("prism:publicationName")
    private String publicationName;
    @JsonProperty("prism:coverDate")
    private Date coverDate;

}
