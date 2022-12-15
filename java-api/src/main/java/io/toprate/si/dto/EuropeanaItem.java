package io.toprate.si.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    //author
    private List<String> dataProvider;
    //image url
    private List<String> edmPreview;

    private List<String> title;

    private List<String> dcDescription;

    private Date timestamp_created;
}
