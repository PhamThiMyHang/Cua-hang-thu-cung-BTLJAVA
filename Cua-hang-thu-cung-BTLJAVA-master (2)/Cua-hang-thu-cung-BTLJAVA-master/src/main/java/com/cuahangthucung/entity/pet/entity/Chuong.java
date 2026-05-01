package com.cuahangthucung.entity.pet.entity;

import com.cuahangthucung.entity.pet.enums.TrangThaiChuong;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "CHUONG")
@Data
public class Chuong {

    @Id
    @Column(name = "MaChuong", length = 20)
    private String maChuong;

    // Khớp với SQL: modify column MaLoaiChuong varchar(20) not null
    @ManyToOne
    @JoinColumn(name = "MaLoaiChuong", nullable = false)
    @NotNull(message = "Loại chuồng không được để trống")
    private LoaiChuong loaiChuong;
    //
    // Khớp với SQL: modify column TrangThai enum('Sửa chữa', 'Trống', 'Kín')
    @NotNull(message = "Trạng thái không được để trống")
    @Enumerated(EnumType.STRING) // Lưu dưới dạng text vào cột Enum của MySQL
    @Column(name = "TrangThai")
    private TrangThaiChuong trangThai = TrangThaiChuong.TRONG;

    /* Lưu ý: Nếu bạn muốn thiết lập mối quan hệ thực sự giữa Chuong và LoaiChuong trong Java,
       bạn có thể dùng @ManyToOne. Nhưng hiện tại, để đơn giản và khớp đúng với các mã
       bạn đã viết ở trên, mình giữ MaLoaiChuong là String.
    */
    @OneToMany(mappedBy = "chuong", fetch = FetchType.LAZY)
    @ToString.Exclude // Tránh vòng lặp vô hạn khi log/print dữ liệu
    private List<Pet> danhSachPet;
}