package com.cuahangthucung.dto.pet;

import com.cuahangthucung.entity.pet.enums.TinhTrangPet;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PetRequest {
    private String maPet; // Để trống khi thêm mới
    private String tenPet;
    private String giong;
    private Integer tuoi;
    private BigDecimal gia;
    private Float canNang;
    private TinhTrangPet tinhTrang;

    // Chỉ nhận ID để Service tự truy vấn Object
    private String maChuong;
    private Integer maKH;
    private Integer maNV;

    private LocalDateTime ngayTra;
}