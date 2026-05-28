package com.cuahangthucung.entity.use.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YeuThichId implements Serializable {

    private Integer maUser;
    private String maSP;
}