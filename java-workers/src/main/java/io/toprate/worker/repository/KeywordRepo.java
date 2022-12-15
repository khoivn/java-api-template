package io.toprate.worker.repository;

import io.toprate.worker.models.Keyword;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KeywordRepo extends MongoRepository<Keyword, ObjectId> {

}
