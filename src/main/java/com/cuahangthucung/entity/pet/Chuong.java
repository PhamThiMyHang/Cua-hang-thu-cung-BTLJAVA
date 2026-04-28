package com.cuahangthucung.entity.pet;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "CHUONG")
@Data
public class Chuong {

    @Id
    @Column(name = "MaChuong", length = 20)
    private String maChuong;

    // Khớp với SQL: modify column MaLoaiChuong varchar(20) not null
    @NotBlank(message = "Mã loại chuồng không được để trống")
    @Column(name = "MaLoaiChuong", nullable = false, length = 20)
    private String maLoaiChuong;

    // Khớp với SQL: modify column TrangThai enum('Sửa chữa', 'Trống', 'Kín')
    @NotNull(message = "Trạng thái không được để trống")
    @Enumerated(EnumType.STRING) // Lưu dưới dạng text vào cột Enum của MySQL
    @Column(name = "TrangThai")
    private TrangThaiChuong trangThai;

    /* Lưu ý: Nếu bạn muốn thiết lập mối quan hệ thực sự giữa Chuong và LoaiChuong trong Java,
       bạn có thể dùng @ManyToOne. Nhưng hiện tại, để đơn giản và khớp đúng với các mã
       bạn đã viết ở trên, mình giữ MaLoaiChuong là String.
    */
}