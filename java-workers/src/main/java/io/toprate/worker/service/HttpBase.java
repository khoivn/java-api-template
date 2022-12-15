package io.toprate.worker.service;

import io.toprate.worker.dto.SearchResultDto;
import io.toprate.worker.statics.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class HttpBase {
    @Autowired
    private RestTemplate restTemplate;


    @Value("${springeropen.url}")
    private String springerUrl;

    @Value("${europeana.url}")
    private String europeanaUrl;

    @Value("${europepmc.url}")
    private String europepmcUrl;

    <T> T get(Resource resource, Map<String, Object> params, Class<T> tClass) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<SearchResultDto> springerEntity = new HttpEntity<>(headers);

        ResponseEntity<T> response = restTemplate.exchange(buildParams(resource, params).toUriString(), HttpMethod.GET, springerEntity, tClass);
        T result = response.getBody();
        if (Objects.isNull(result)) {
            throw new RuntimeException();
        }
        return result;
    }

    private String getUrl(Resource resource) {
        String url;
        switch (resource) {
            case ELSEVIER:
                url = "link1";
                break;
            case SPRINGER:
                url = springerUrl;
                break;
            case EUROPEANA:
                url = europeanaUrl;
                break;
            case EUROPEPMC:
                url = europepmcUrl;
                break;
            default:
                url = null;
        }

        return url;
    }

    private UriComponents buildParams(Resource resource, Map<String, Object> params) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(getUrl(resource));
        params.forEach(uriComponentsBuilder::queryParam);
        return uriComponentsBuilder.build();
    }
}
