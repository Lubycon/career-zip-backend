package com.careerzip.global.newsletter;

import com.nimbusds.common.contenttype.ContentType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Getter
@Component
public class GetResponseClient {

    private final GetResponseProperties getResponseProperties;

    public <T> T getRequest(String requestPath, Class<T> payloadType) {
        RestTemplate restTemplate = new RestTemplate();
        URI requestURI = createGetResponseRequestURI(requestPath);
        HttpEntity requestEntity = createRequestEntity();
        ResponseEntity<T> response = restTemplate.exchange(requestURI, HttpMethod.GET, requestEntity, payloadType);
        return response.getBody();
    }

    private HttpEntity createRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(getResponseProperties.getAuthHeaderName(), getResponseProperties.getApiKey());
        headers.add(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);
        return new HttpEntity(headers);
    }

    private URI createGetResponseRequestURI(String requestPath) {
        return UriComponentsBuilder.fromHttpUrl(getResponseProperties.getBaseUrl())
                                   .path(requestPath)
                                   .build()
                                   .toUri();
    }
}
