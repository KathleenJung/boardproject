package com.example.board.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class LocationCode implements Serializable {

    String step1;
    String step2;
    String step3;
    int x;
    int y;
    int longitude_h;
    int longitude_m;
    double longitude_s;
    int latitude_h;
    int latitude_m;
    double latitude_s;
    double longitude_s_100;
    double latitude_s_100;
    String mid_land_code;
    String mid_Temperature_code;
    @EmbeddedId
    private LocationCodeId locationCodeId;
}
