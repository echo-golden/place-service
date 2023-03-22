package com.search.place.application.search.request;

import org.springframework.util.MultiValueMap;

public interface ExternalSearchRequest {
    String getKeyword();
    MultiValueMap<String, String> getHttpHeaders();
    MultiValueMap<String, String> getQueryParams();
}
