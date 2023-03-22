# 장소 검색 서비스


## 장소 검색 keyworld=천호곱창
### 요청
curl -X GET \
'http://localhost:8080/v1/place?keyword=%EC%B2%9C%ED%98%B8%EA%B3%B1%EC%B0%BD' \
-H 'Accept: application/json' -iv

### 응답
{
"code": "0000",
"message": "조회가 완료되었습니다.",
"count": 8,
"places": [
{
"placeName": "천호소곱창"
},
{
"placeName": "신사소곱창 천호점"
},
{
"placeName": "천호곱창"
},
{
"placeName": "천호곱창 천호본점"
},
{
"placeName": "천호곱창 상암점"
},
{
"placeName": "삼끼막창 천호본점"
},
{
"placeName": "대팔이네"
},
{
"placeName": "곱창팩토리 천호본점"
}
]
}

## 키워드 목록 조회
### 요청
curl -X GET \
http://localhost:8080/v1/place/keywords \
-H 'Accept: application/json' -iv

### 응답
{
"code": "0000",
"message": "조회가 완료되었습니다.",
"count": 10,
"keywords": [
{
"keyword": "곱창",
"count": 41
},
{
"keyword": "치과",
"count": 39
},
{
"keyword": "천호곱창",
"count": 38
},
{
"keyword": "판교주차장",
"count": 3
},
{
"keyword": "시낳능ㄴ행",
"count": 2
},
{
"keyword": "천호맛집",
"count": 2
},
{
"keyword": "판교역맛집",
"count": 1
},
{
"keyword": "판교맛집",
"count": 1
},
{
"keyword": "근처세차장",
"count": 1
},
{
"keyword": "강동구세차장",
"count": 1
}
]
}

### 동시성 처리

장소 검색으로 키워드와 검색 횟수를 DB 에 저장할 때
 - 검색 횟수에 대한 동시성 처리를 위해 Redisson redis client 를 사용합니다
 - Redission 은 lock 이 해제되었는지 지속적으로 redis server 에 polling 하는 spin 락과 달리
 - pub/sub 구조로 lock 이 해제되었을 때 임계영역 점유를 위해 대기하는 트랜잭션(thread)에게 publish 하여 lock 해제를 알림으로써
 - 실시간 대용량 트래픽을 처리하는 서비스에서 redis server 에 부하를 줄일 수 있습니다.


### 조회 성능 개선을 위한 cache 적용
 - 키워드 목록 조회 결과를 cache 에 저장합니다
 - 정확한 조회 결과를 위해 장소 검색 신규 호출이 발생 시 cache 를 삭제합니다

