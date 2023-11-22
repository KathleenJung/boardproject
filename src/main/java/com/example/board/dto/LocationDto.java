package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationDto {
    private int x;
    private int y;
    private String mid_land_code;
    private String mid_temperature_code;
}
