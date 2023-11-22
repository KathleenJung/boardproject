package com.example.board.dto;

import lombok.Data;

@Data
public class MidWeatherDto {
    int min; // 최저 기온
    int max; // 최고 기온
    int rnStAm; // 오전 강수 확률
    int rnStPm; // 오후 강수 확률
    String wfAm; // 오전 날씨 예보
    String wfPm; // 오후 날씨 예보
}