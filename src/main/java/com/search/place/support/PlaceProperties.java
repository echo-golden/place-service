package com.search.place.support;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "place.api")
public class PlaceProperties {
    private KakaoProperties kakao;
    private NaverProperties naver;

    @Getter
    @Setter
    public static class KakaoProperties {
        private String url;
        private String restApiKey;
        private String displayCount;
    }

    @Getter
    @Setter
    public static class NaverProperties {
        private String url;
        private String clientId;
        private String clientSecret;
        private String displayCount;
    }
}
