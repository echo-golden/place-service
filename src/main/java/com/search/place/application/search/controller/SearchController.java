package com.search.place.application.search.controller;

import com.search.place.application.search.response.PlaceSearchResponse;
import com.search.place.application.search.service.SearchServiceHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchServiceHandler searchServiceHandler;

    @GetMapping("/v1/place")
    @ResponseBody
    public ResponseEntity<?> searchPlace(@RequestParam("keyword") @NonNull String keyword) throws InterruptedException {

        PlaceSearchResponse resp = searchServiceHandler.search(keyword);
        return ResponseEntity.ok(resp);
    }
}
