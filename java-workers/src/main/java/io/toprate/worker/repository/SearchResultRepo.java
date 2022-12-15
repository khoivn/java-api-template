package io.toprate.worker.repository;

import io.toprate.worker.models.SearchResult;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SearchResultRepo extends MongoRepository<SearchResult, ObjectId> {
    List<SearchResult> findByResourceIdIn(List<String> ids);
}
