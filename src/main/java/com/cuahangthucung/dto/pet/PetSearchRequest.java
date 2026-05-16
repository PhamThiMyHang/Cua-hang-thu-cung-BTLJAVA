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

    // Thêm các trường này để tìm kiếm theo TÊN
    private String tenKH;
    private String tenNV;

    // Vẫn giữ ID để lọc chính xác khi cần
    private Integer maKH;
    private Integer maNV;
/*
    // Hỗ trợ sắp xếp và phân trang
    private String sortBy = "maPet";
    private String sortDir = "asc";*/

}