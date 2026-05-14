package com.cuahangthucung.repository.pet;

import com.cuahangthucung.dto.pet.LoaiChuongSearchRequest;
import com.cuahangthucung.entity.pet.entity.LoaiChuong;
import com.cuahangthucung.repository.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class LoaiChuongSpecification {

    public static Specification<LoaiChuong> getFilter(LoaiChuongSearchRequest request) {
        if (request == null) return null;

        return Specification.allOf(
                // 1. Lọc theo tên loại (Chứa ký tự - Like)
                GenericSpecification.fieldContains("tenLoai", request.getTenLoai()),

                // 2. Lọc theo khoảng giá thuê (Between)
                GenericSpecification.<LoaiChuong, BigDecimal>fieldBetween(
                        "giaThue",
                        request.getGiaThueMin(),
                        request.getGiaThueMax()
                )
        );
    }
}