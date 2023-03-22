package com.search.place.application.inquiry.service;

import com.search.place.application.inquiry.response.KeywordInquiryResponse;
import com.search.place.domain.entity.Place;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class KeywordInquiryServiceImplTest {
    @Autowired KeywordInquiryServiceImpl keywordInquiryService;


}