package io.toprate.si.service;

import io.toprate.si.dto.SearchResultDto;
import io.toprate.si.hashKey.HashKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisBase {
    @Autowired
    RedisTemplate<String, List<SearchResultDto>> template;

    public List<SearchResultDto> getFromRedis(String query, HashKey hashKey) {
        HashOperations<String, HashKey, List<SearchResultDto>> hashOperations = template.opsForHash();
        return hashOperations.get(query, hashKey);
    }

    void saveToRedis(String query, List<SearchResultDto> resultDtos, HashKey page) {
        template.opsForHash().put(query, page, resultDtos);
    }
}
