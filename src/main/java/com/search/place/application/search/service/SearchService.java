package com.search.place.application.search.service;

import com.search.place.application.search.response.PlaceInfo;
import com.search.place.support.exception.ExternalSearchException;

import java.util.List;

public interface SearchService {
    List<PlaceInfo> search(String keyword) throws ExternalSearchException;
}
