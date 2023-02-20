package com.elvisespinoza.dinnerreview.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Restaurant {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String city;
    private String state;
    private String overallScore;
    private String peanutScore;
    private String eggScore;
    private String dairyScore;

}
