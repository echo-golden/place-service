package com.search.place.application.search.response;

import com.search.place.application.ResponseCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaceSearchResponse {
    private String code;
    private String message;
    private long count;
    private List<PlaceInfo> places;

    public static PlaceSearchResponse error(ResponseCode responseCode) {
        PlaceSearchResponse placeSearchResponse = new PlaceSearchResponse();
        placeSearchResponse.code = responseCode.getCode();
        placeSearchResponse.message = responseCode.getMessage();
        return placeSearchResponse;
    }
}
