package com.cuahangthucung.repository.pet;

import com.cuahangthucung.entity.pet.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, String>, JpaSpecificationExecutor<Pet> {
    // Giữ lại hàm đặc thù này để sinh mã tự động
    @Query("SELECT p FROM Pet p WHERE p.maPet LIKE :prefix% ORDER BY p.maPet DESC LIMIT 1")
    Optional<Pet> findLastPetByPrefix(@Param("prefix") String prefix);

    // 2. Phục vụ PetSummaryDTO: Tính toán tổng quát

    @Query("SELECT COUNT(p) FROM Pet p")
    Long countAllPets();

    @Query("SELECT SUM(p.gia) FROM Pet p")
    BigDecimal sumAllValue();

    // Thống kê theo tình trạng (Ví dụ: đếm số pet đang điều trị/bệnh)
    @Query("SELECT COUNT(p) FROM Pet p WHERE p.tinhTrang = 'BENH'")
    Long countSickPets();

    // Thống kê pet mới trong tháng hiện tại (Phục vụ Dashboard)
    @Query("SELECT COUNT(p) FROM Pet p WHERE p.maPet LIKE :monthPrefix%")
    Long countNewPetsInMonth(@Param("monthPrefix") String monthPrefix);

    // Thống kê số lượng pet của 1 khách hàng (dựa vào ID)
    @Query("SELECT COUNT(p) FROM Pet p WHERE p.khachHang.maKH = :maKH")
    Long countByMaKH(@Param("maKH") Integer maKH);

    // Thống kê số lượng pet do 1 nhân viên phụ trách (dựa vào ID)
    @Query("SELECT COUNT(p) FROM Pet p WHERE p.nhanVien.maNV = :maNV")
    Long countByMaNV(@Param("maNV") Integer maNV);

    // Thống kê số lượng pet của khách hàng dựa vào TÊN (Tìm kiếm tương đối)
    @Query("SELECT COUNT(p) FROM Pet p WHERE p.khachHang.tenKH LIKE %:tenKH%")
    Long countByTenKH(@Param("tenKH") String tenKH);


/*15/05/2026 Pham Thi My Hang*/

        // Tìm kiếm thú cưng theo tên (Spring Data JPA sẽ tự tạo query)
        Optional<Pet> findByTenPet(String tenPet);


}