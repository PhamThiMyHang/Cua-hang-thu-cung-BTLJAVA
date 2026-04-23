package com.cuahangthucung.entity;

import lombok.Getter;

@Getter
public enum TrangThaiChuong {
    SUA_CHUA("Sua_Chua"),
    TRONG("Trong"),
    KIN("Kin");

    private final String value;

    TrangThaiChuong(String value) {
        this.value = value;
    }
}