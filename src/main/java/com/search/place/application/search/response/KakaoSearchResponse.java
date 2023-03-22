package com.search.place.application.search.response;

import lombok.Getter;

import java.util.List;

@Getter
public class KakaoSearchResponse {
    private List<Document> documents;
    private Meta meta;

    @Getter
    public static class Document {
        private String id;
        private String place_name;
        private String category_name;
        private String category_group_code;
        private String category_group_name;
        private String phone;
        private String address_name;
        private String road_address_name;
        private String x;
        private String y;
        private String place_url;
        private String distance;
    }

    @Getter
    public static class Meta {
        private int total_count;
        private int pageable_count;
        private boolean is_end;
        private RegionInfo same_name;
    }

    @Getter
    public static class RegionInfo {
        private List<String> region;
        private String keyword;
        private String selected_region;
    }
}
