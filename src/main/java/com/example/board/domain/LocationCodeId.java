package com.example.board.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class LocationCodeId implements Serializable {
    private String sortation;
    private long districtCode;
}