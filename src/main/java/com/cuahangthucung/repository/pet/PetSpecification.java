package com.cuahangthucung.repository.pet;

import com.cuahangthucung.dto.pet.PetSearchRequest;
import com.cuahangthucung.entity.pet.entity.Pet;
import com.cuahangthucung.repository.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;
import java.math.BigDecimal;

public class PetSpecification {

    public static Specification<Pet> getFilter(PetSearchRequest request) {
        if (request == null) return null;

        return Specification.allOf(
                GenericSpecification.fieldContains("tenPet", request.getTenPet()),
                GenericSpecification.fieldEquals("giong", request.getGiong()),
                GenericSpecification.fieldEquals("tinhTrang", request.getTinhTrang()),

                // Lọc theo ID (Khóa ngoại)
                GenericSpecification.fieldEquals("khachHang.maKH", request.getMaKH()),
                GenericSpecification.fieldEquals("nhanVien.maNV", request.getMaNV()),

                // TÌM KIẾM THEO TÊN (Join bảng)
                GenericSpecification.fieldContains("khachHang.tenKH", request.getTenKH()),
                GenericSpecification.fieldContains("nhanVien.tenNV", request.getTenNV()),

                GenericSpecification.fieldEquals("chuong.maChuong", request.getMaChuong()),
                GenericSpecification.<Pet, BigDecimal>fieldBetween("gia", request.getGiaMin(), request.getGiaMax())
        );
    }
}