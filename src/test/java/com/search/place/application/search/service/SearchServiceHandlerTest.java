package com.search.place.application.search.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Test
    public void 장소_검색() throws InterruptedException {
        String keyword = "천호곱창";
        kakaoSearchService.search(keyword);
        searchServiceHandler.search(keyword);
    }
}