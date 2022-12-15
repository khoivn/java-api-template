package io.toprate.si.models;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@SuperBuilder(toBuilder = true)
public class BaseModel {
    @Id
    @Field("_id")
    private ObjectId id;
    private Date createdAt;

    private Date updatedAt;

    public BaseModel() {
        createdAt = new Date();
        updatedAt = new Date();
    }
}


