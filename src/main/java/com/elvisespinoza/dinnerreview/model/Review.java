package com.elvisespinoza.dinnerreview.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Review {
    @Id
    @GeneratedValue
    private Long id;

    private String submitted;
    private Long restaurant;
    private Integer peanutScore;
    private Integer eggScore;
    private Integer dairyScore;
    private String comment;
}
