package io.toprate.si.repository;

import io.toprate.si.models.Keyword;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KeywordRepo extends MongoRepository<Keyword, ObjectId> {
    Keyword findByKeyword(String keyword);
}
