package com.search.place.domain.repository;

import com.search.place.domain.entity.Place;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, String> {
    @Cacheable(cacheNames = "keywords")
    List<Place> findTop10ByOrderByCountDesc();
}
