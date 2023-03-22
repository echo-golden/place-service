package com.search.place.application.search.service;

import com.search.place.application.inquiry.response.KeywordInquiryResponse;
import com.search.place.application.inquiry.service.KeywordInquiryServiceImpl;
import com.search.place.application.search.response.PlaceSearchResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SearchServiceHandlerTest {
    @Autowired SearchServiceHandler searchServiceHandler;
    @Autowired KakaoSearchServiceImpl kakaoSearchService;
    @Autowired NaverSearchServiceImpl naverSearchService;
    @Autowired KeywordInquiryServiceImpl keywordInquiryService;

    @Test
    public void 장소_검색() throws InterruptedException {
        String keyword = "판교맛집";
        PlaceSearchResponse resp = searchServiceHandler.search(keyword);

        assertEquals(10, resp.getCount());
    }

    @org.junit.Test
    public void 키워드_목록_조회() {
        KeywordInquiryResponse resp =  keywordInquiryService.keywords();


    }

}