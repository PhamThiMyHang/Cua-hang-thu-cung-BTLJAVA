package com.cuahangthucung.entity.user.entity;

import com.cuahangthucung.entity.user.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "USERS")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Integer userID;

    @Column(name = "Username", nullable = false, unique = true, length = 50)
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Column(name = "Password", nullable = false, length = 255)
    private String password;

    @NotNull(message = "Trạng thái không được để trống")
    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private UserStatus status = UserStatus.ACTIVE;

    // Bổ sung thuộc tính Gmail đồng bộ với DB
    @NotBlank(message = "Gmail không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Hệ thống chỉ chấp nhận tài khoản @gmail.com")
    @Column(name = "Gmail", nullable = false, unique = true, length = 100)
    private String gmail;

    // ==================== SỬA Ở ĐÂY ====================
    @NotNull(message = "Vai trò không được để trống")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "USER_ROLES",
        joinColumns = @JoinColumn(name = "UserID"),
        inverseJoinColumns = @JoinColumn(name = "RoleID")
    )
    private Set<Role> roles = new HashSet<>();

    // Chuyển thành @OneToOne vì quan hệ tài khoản - nhân viên là 1-1 (UserID là UNIQUE)
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    @ToString.Exclude
    private NhanVien nhanVien;

    // Chuyển thành @OneToOne tương tự cho khách hàng
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    @ToString.Exclude
    private KhachHang khachHang;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<LichSuDangNhap> lichSuDangNhap;


    
    
}