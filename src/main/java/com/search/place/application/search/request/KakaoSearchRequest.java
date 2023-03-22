package com.search.place.application.search.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@Setter
@RequiredArgsConstructor(staticName = "of")
public class KakaoSearchRequest implements ExternalSearchRequest {
    private final String keyword;
    private final String displayCount;
    private final MultiValueMap<String, String> httpHeaders;

    @Override
    public String getKeyword() {
        return keyword;
    }

    @Override
    public MultiValueMap<String, String> getHttpHeaders() {
        return httpHeaders;
    }

    @Override
    public MultiValueMap<String, String> getQueryParams() {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("query", keyword);
        queryParams.add("size", displayCount);
        return queryParams;
    }
}
