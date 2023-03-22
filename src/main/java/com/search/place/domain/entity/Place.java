package com.search.place.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class Place implements Serializable {
    @Id
    private String keyword;
    private long count;
}
