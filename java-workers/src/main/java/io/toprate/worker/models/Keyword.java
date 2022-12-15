package io.toprate.worker.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("keyword")
@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Keyword extends BaseModel{
    @BsonProperty("keyword")
    private String keyword;
}
