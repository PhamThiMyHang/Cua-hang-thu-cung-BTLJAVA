package com.cuahangthucung.dto.pet;

import com.cuahangthucung.entity.pet.enums.TinhTrangPet;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PetSearchRequest {
    private String tenPet;
    private String giong;
    private TinhTrangPet tinhTrang;
    private String maChuong;
    private BigDecimal giaMin;
    private BigDecimal giaMax;
<<<<<<< HEAD
/*
    // Hỗ trợ sắp xếp và phân trang
    private String sortBy = "maPet";
    private String sortDir = "asc";*/
=======

    // Hỗ trợ sắp xếp và phân trang
    private String sortBy = "maPet";
    private String sortDir = "asc";
>>>>>>> DoanThiNgocGiau
}