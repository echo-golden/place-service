package com.search.place.application.inquiry.controller;

import com.search.place.application.inquiry.service.KeywordInquiryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InquiryController {

    private final KeywordInquiryServiceImpl keywordInquiryService;

    @GetMapping("/v1/place/keywords")
    public ResponseEntity<?> keywordList() {
        return ResponseEntity.ok(keywordInquiryService.keywords());
    }
}
