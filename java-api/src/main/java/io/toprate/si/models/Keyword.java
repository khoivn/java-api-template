package io.toprate.si.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document("keyword")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Keyword extends BaseModel {
    @BsonProperty("keyword")
    private String keyword;
}
