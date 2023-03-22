package com.search.place.application.search.service;

import com.search.place.application.ResponseCode;
import com.search.place.application.search.response.PlaceInfo;
import com.search.place.application.search.response.PlaceSearchResponse;
import com.search.place.domain.entity.Place;
import com.search.place.domain.repository.PlaceRepository;
import com.search.place.support.exception.ExternalSearchException;
import com.search.place.support.exception.InternalSearchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceHandler {

    private final PlaceRepository placeRepository;
    private final KakaoSearchServiceImpl kakaoSearchService;
    private final NaverSearchServiceImpl naverSearchService;
    private final RedissonClient redissonClient;

    /**
     * 검색 키워드와 검색 횟수를 DB 에 저장하고
     * kakao 와 naver open api 사용하여 장소 검색
     * @param keyword
     * @return
     */
    @CacheEvict(cacheNames = "keywords")
    public PlaceSearchResponse search(String keyword) throws InterruptedException {

        Place place = new Place();
        place.setKeyword(keyword);
        place.setCount(1);

        RLock rLock = redissonClient.getLock(keyword);

        // lock 획들을 위해 1초 대기하며, 획득된 락은 3초를 초과하면 자동으로 해제됨
        if (!rLock.tryLock(1000, 3000, TimeUnit.MILLISECONDS)) {
            log.error("lock 획득 실패 keyword({})", keyword);
            throw new InternalSearchException(ResponseCode.INTERNAL_SERVICE_ERROR);
        }

        try {
            this.placeRepository.findById(keyword).ifPresentOrElse(
                    p -> {
                        p.setCount(p.getCount() + 1);
                        placeRepository.save(p); },
                    () -> placeRepository.save(place));

        } finally {
            if (rLock.isLocked()) {
                rLock.unlock();
            }
        }

        try {
            return merge(
                    kakaoSearchService.search(keyword),
                    naverSearchService.search(keyword));
        } catch (ExternalSearchException e) {
            return PlaceSearchResponse.error(e.getResponseCode());
        }
    }

    /**
     * 카카오 결과를 기준 순서로 사용
     * 카카오와 네이버 결과에 모두 존재하면 상위 정렬
     * 둘 중 하나만 존재하면 카카오 결과를 우선 배치
     *
     * @param primaryPlaceList
     * @param secondaryPlaceList
     * @return 우선순위 고려하여 정렬된 장소 목록과 검색된 장소의 개수를 리턴
     */
    public PlaceSearchResponse merge(List<PlaceInfo> primaryPlaceList, List<PlaceInfo> secondaryPlaceList) {

        List<PlaceInfo> refinedPlaceList = new ArrayList<>();

        for (PlaceInfo k : primaryPlaceList) {
            // kakao 와 naver 의 검색 결과 장소명이 같은 경우
            secondaryPlaceList.stream().filter(n -> n.getPlaceName().equals(k.getPlaceName())).findFirst().ifPresent(n -> {
                System.out.println("kakao(" + k.getPlaceName() + ") = naver(" + n.getPlaceName() + ")");

                refinedPlaceList.add(k); // kakao 와 naver 결과에 모두 존재하는 장소
                secondaryPlaceList.remove(n); // naver 에만 존재하는 장소를 보관하기 위해서 kakao 와 placeName 이 동일한 naver 객체는 loop 내에서 삭제
            });
        }

        // placeInfoListByKakao list 의 요소 삭제는 index 변경 고려하여 for loop 외부에서 처리
        primaryPlaceList.removeAll(refinedPlaceList);

        // 둘 다 존재하는 장소 목록에 kakao, naver 순서대로 추가
        refinedPlaceList.addAll(primaryPlaceList);
        refinedPlaceList.addAll(secondaryPlaceList);

        PlaceSearchResponse resp = new PlaceSearchResponse();
        resp.setCount(refinedPlaceList.size());
        resp.setPlaces(refinedPlaceList);
        resp.setCode(ResponseCode.SUCCESS.getCode());
        resp.setMessage(ResponseCode.SUCCESS.getMessage());

        return resp;
    }
}
