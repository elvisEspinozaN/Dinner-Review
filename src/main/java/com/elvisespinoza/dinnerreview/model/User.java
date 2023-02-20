package com.elvisespinoza.dinnerreview.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String displayName;
    private String city;
    private String state;
    private String zipCode;
    private boolean peanutInterested;
    private boolean eggInterested;
    private boolean dairyInterested;
}
