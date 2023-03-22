package com.search.place.application.search.service;

import com.search.place.application.search.request.KakaoSearchRequest;
import com.search.place.application.search.response.KakaoSearchResponse;
import com.search.place.application.search.response.PlaceInfo;
import com.search.place.support.PlaceProperties;
import com.search.place.support.exception.ExternalSearchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KakaoSearchServiceImpl implements SearchService {
    private final PlaceProperties placeProperties;
    private final WebClientService webClientService;

    @Override
    public List<PlaceInfo> search(String keyword) throws ExternalSearchException {

        MultiValueMap<String, String> reqHeaders = new LinkedMultiValueMap<>();
        reqHeaders.add("Authorization", "KakaoAK " + placeProperties.getKakao().getRestApiKey());

        // kakao open api 호출
        KakaoSearchResponse kakaoSearchResponse;
        kakaoSearchResponse = webClientService.request(
                placeProperties.getKakao().getUrl(),
                KakaoSearchRequest.of(keyword, placeProperties.getKakao().getDisplayCount(), reqHeaders),
                KakaoSearchResponse.class);


        List<PlaceInfo> placeInfos = kakaoSearchResponse.getDocuments().stream().map(d -> PlaceInfo.of(d.getPlace_name())).collect(Collectors.toList());
        placeInfos.forEach(i -> System.out.println("kakao = " + i.getPlaceName()));
        return placeInfos;
    }
}
