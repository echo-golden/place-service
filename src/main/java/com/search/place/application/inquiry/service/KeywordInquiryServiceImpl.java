package com.search.place.application.inquiry.service;

import com.search.place.application.ResponseCode;
import com.search.place.application.inquiry.response.KeywordInquiryResponse;
import com.search.place.domain.entity.Place;
import com.search.place.domain.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeywordInquiryServiceImpl implements InquiryService {

    private final PlaceRepository placeRepository;

    @Override
    public KeywordInquiryResponse keywords() {
        List<Place> placeList = placeRepository.findTop10ByOrderByCountDesc();

        //TODO builder 로 변경
        KeywordInquiryResponse keywordInquiryResponse = new KeywordInquiryResponse();
        keywordInquiryResponse.setCode(ResponseCode.SUCCESS.getCode());
        keywordInquiryResponse.setMessage(ResponseCode.SUCCESS.getMessage());
        keywordInquiryResponse.setCount(placeList.size());
        keywordInquiryResponse.setKeywords(placeList);
        return keywordInquiryResponse;
    }
}
