package com.search.place.application.search.response;

import lombok.Getter;

import java.util.List;

@Getter
public class NaverSearchResponse {
    private String lastBuildDate; //"Mon, 20 Mar 2023 20:52:57 +0900"
    private int total;
    private int start;
    private int display;
    private List<Item> items;

    @Getter
    public static class Item {
        private String title;
        private String link;
        private String category;
        private String description;
        private String telephone;
        private String address;
        private String roadAddress;
        private String mapx;
        private String mapy;
    }
}
