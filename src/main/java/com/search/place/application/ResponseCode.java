package com.search.place.application;

import lombok.Getter;

public enum ResponseCode {
    SUCCESS("0000", "조회가 완료되었습니다."),
    EXTERNAL_API_4xx_ERROR("1400","내부 서비스 오류로 장소 검색에 실패했습니다."),
    EXTERNAL_API_5xx_ERROR("1500","장소 검색에 실패했습니다. 잠시 후 다시 요청해 주세요."),
    EXTERNAL_API_SERVICE_ERROR("1600","검색 API 호출에 실패했습니다."),
    INTERNAL_SERVICE_ERROR("0500","내부 서비스 오류로 검색에 실패했습니다. 잠시 후 다시 요청해 주세요.");

    @Getter
    private String code;
    @Getter
    private String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
