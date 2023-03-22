package com.search.place.application.search.service;

import com.search.place.application.ResponseCode;
import com.search.place.application.search.request.ExternalSearchRequest;
import com.search.place.support.exception.ExternalSearchException;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class WebClientService {
    private final WebClient webClient;

    @Autowired
    public WebClientService(WebClient.Builder webClientBuilder) {

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .responseTimeout(Duration.ofMillis(3000))
                .doOnConnected(connection -> connection.addHandlerLast(new ReadTimeoutHandler(3000, TimeUnit.MILLISECONDS)));

        this.webClient = webClientBuilder
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    public <T, R> R request(String url, ExternalSearchRequest req, Class<R> classOfResp) {
        try {
            return this.webClient.mutate()
                    .baseUrl(url)
                    .defaultHeaders(headers -> headers.addAll(req.getHttpHeaders()))
                    .build()
                    .get()
                    .uri(uriBuilder -> uriBuilder.queryParams(req.getQueryParams()).build())
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new ExternalSearchException(ResponseCode.EXTERNAL_API_4xx_ERROR)))
                    .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new ExternalSearchException(ResponseCode.EXTERNAL_API_5xx_ERROR)))
                    .bodyToMono(classOfResp)
                    .block();
        } catch (Exception e) {
            log.error("", e);
            throw new ExternalSearchException(ResponseCode.EXTERNAL_API_SERVICE_ERROR);
        }
    }
}
