package io.toprate.worker.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KeywordMessage implements Serializable {

    @JsonProperty("keyword")
    String keyword;

}