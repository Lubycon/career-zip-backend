package com.careerzip.global.newsletter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@Getter
@Component
public class GetResponseClient {

    private final GetResponseProperties getResponseProperties;

    public <T> T requestGetResponse(String requestPath, HttpMethod httpMethod, Class<T> payloadType) {
        RestTemplate restTemplate = new RestTemplate();
        URI requestURI = createGetResponseRequestURI(requestPath);
        HttpEntity requestEntity = createRequestEntity();
        ResponseEntity<T> response = restTemplate.exchange(requestURI, httpMethod, requestEntity, payloadType);
        return response.getBody();
    }

    private HttpEntity createRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(getResponseProperties.getAuthHeaderName(), getResponseProperties.getApiKey());
        return new HttpEntity(headers);
    }

    private URI createGetResponseRequestURI(String requestPath) {
        return UriComponentsBuilder.fromHttpUrl(getResponseProperties.getBaseUrl())
                                   .path(requestPath)
                                   .build()
                                   .toUri();
    }
}
