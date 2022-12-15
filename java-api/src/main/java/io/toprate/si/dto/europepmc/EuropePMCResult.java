package io.toprate.si.dto.europepmc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EuropePMCResult {
    private String imageUrl;

    private String authorString;

    private String title;

    private String journalTitle;

    private Date firstPublicationDate;
}
