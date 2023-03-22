package com.search.place.application.search.service;

import com.search.place.support.PlaceProperties;
import com.search.place.application.search.request.NaverSearchRequest;
import com.search.place.application.search.response.NaverSearchResponse;
import com.search.place.application.search.response.PlaceInfo;
import com.search.place.support.exception.ExternalSearchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NaverSearchServiceImpl implements SearchService {
    private final PlaceProperties placeProperties;
    private final WebClientService webClientService;

    @Override
    public List<PlaceInfo> search(String keyword) throws ExternalSearchException {

        MultiValueMap<String, String> reqHeaders = new LinkedMultiValueMap<>();
        reqHeaders.add("X-Naver-Client-Id", placeProperties.getNaver().getClientId());
        reqHeaders.add("X-Naver-Client-Secret", placeProperties.getNaver().getClientSecret());

        // naver open api 호출
        NaverSearchResponse naverSearchResponse;
        naverSearchResponse = webClientService.request(
                placeProperties.getNaver().getUrl(),
                NaverSearchRequest.of(keyword, placeProperties.getNaver().getDisplayCount(), reqHeaders),
                NaverSearchResponse.class);

        List<PlaceInfo> placeInfos = naverSearchResponse.getItems().stream().map(i -> PlaceInfo.of(i.getTitle().replaceAll("<[^>]*>", ""))).collect(Collectors.toList());
        placeInfos.forEach(i -> System.out.println("naver = " + i.getPlaceName()));
        return placeInfos;
    }
}
