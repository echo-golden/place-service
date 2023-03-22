package com.search.place.application.inquiry.response;

import com.search.place.domain.entity.Place;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KeywordInquiryResponse {
    private String code;
    private String message;
    private long count;
    private List<Place> keywords;
}
