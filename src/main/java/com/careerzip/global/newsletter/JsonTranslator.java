package com.careerzip.global.newsletter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JsonTranslator {

    private final ObjectMapper objectMapper;

    public <T> List<T> mapToList(String json, Class<T> responseType) {
        try {
            return new ArrayList<>(objectMapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, responseType)));
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: 구체적인 예외 처리 필요
            throw new RuntimeException();
        }
    }
}
