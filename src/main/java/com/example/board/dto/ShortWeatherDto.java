package com.example.board.dto;

import lombok.Data;

@Data
public class ShortWeatherDto {
    String category;
    String fcstDate;
    String fcstTime;
    Object fcstValue;
    int nx;
    int ny;
}
