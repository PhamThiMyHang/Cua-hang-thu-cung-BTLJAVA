package com.cuahangthucung.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PET")
@Data
public class Pet {
    /*MaPet sẽ được thực hiện một cách tự động, ví dụ P260401 với P là cố định, 26 sẽ lấy từ hai số cuối cùng củanamwm thêm thú cưng vào danh sách quảnlysys, 04 là tháng và 01 để đánh ấu thú cưng là thú cưng được gửi vào đầu tiên của tháng*/
    @Id
    @Column(name = "MaPet", length = 20)
    private String maPet;


    @Size(min = 2, max = 100, message = "Tên phải từ 2 đến 100 ký tự")
    @Column(name = "TenPet", nullable = true)
    private String tenPet ;

    @Size(min = 2, max = 100, message = "Tên phải từ 2 đến 100 ký tự")
    @Column(name = "Giong", nullable = true)
    private String giong;


    @NotNull(message = "Tuổi không được để trống")
    @Min(value = 0, message = "Tuổi không được là số âm")
    @Column(name = "Tuoi", nullable = false)
    private Integer tuoi = 0;

    @DecimalMin(value = "0.0", inclusive = true, message = "Giá phải lớn hơn 0")
    @Column(name = "Gia", nullable = false)
    private BigDecimal gia = BigDecimal.ZERO; // Gán mặc định là 0 ở đây

    @NotNull(message = "Cân nặng không được để trống")
    @Min(value = 0, message = "Cân nặng không được là số âm") // Đổi Positive thành Min(0) để khớp với mặc định = 0
    @Max(value = 150, message = "Cân nặng không được vượt quá 150 kg")
    @Column(name = "CanNang", nullable = false)
    private Float canNang = 0.0f;

    @Column(name = "TinhTrang", nullable = true)
    private String tinhTrang;


    @Column(name = "MaChuong", nullable = true)
    private String maChuong;


    @NotBlank(message = "Mã khách hàng không được để trống")
    @Column(name = "MaKH", nullable = false)
    private String maKH;

    @NotBlank(message = "Mã nhân viên không được để trống")
    @Column(name = "MaNV", nullable = false)
    private String maNV;

    /*Bổ sung thêm 2 cột là: NgayGui, NgayTra, NgayGui lấy từ hệ thống ngày nhập, ko để trống, NgayTrả có thể không nhập */

    @NotNull(message = "Ngày gửi không được để trống")
    @Column(name = "NgayGui", nullable = false, updatable = false)
    private LocalDateTime ngayGui;

    @Column(name = "NgayTra")
    private LocalDateTime ngayTra;

    @PrePersist
    protected void onCreate() {
        if (this.ngayGui == null) {
            this.ngayGui = LocalDateTime.now();
        }
    }

}