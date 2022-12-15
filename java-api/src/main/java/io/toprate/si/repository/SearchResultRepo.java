package io.toprate.si.repository;

import io.toprate.si.models.SearchResult;
import io.toprate.si.statics.Resource;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface SearchResultRepo extends MongoRepository<SearchResult, ObjectId> {

    Page<SearchResult> findByKeywordIdsAndResource(ObjectId id, Resource resource, Pageable pageable);
    Page<SearchResult> findByResource(Resource resource, Pageable pageable);
}
