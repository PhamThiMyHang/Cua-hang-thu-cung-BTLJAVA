package com.cuahangthucung.repository.pet;

import com.cuahangthucung.entity.pet.entity.PetImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PetImageRepository extends JpaRepository<PetImage, Integer>, JpaSpecificationExecutor<PetImage> {

    // 2. Tính số bản ghi của một pet theo mã
    long countByPet_MaPet(String maPet);

    // 3. Tính số bản ghi trong một ngày (Sử dụng khoảng LocalDateTime)
    @Query("SELECT COUNT(pi) FROM PetImage pi WHERE pi.thoiGianDangTai BETWEEN :start AND :end")
    long countByDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    // 5. Tính số bản ghi của 1 pet cụ thể vào 1 ngày cụ thể
    @Query("SELECT COUNT(pi) FROM PetImage pi WHERE pi.pet.maPet = :maPet " +
            "AND pi.thoiGianDangTai BETWEEN :start AND :end")
    long countByPetAndDateRange(@Param("maPet") String maPet,
                                @Param("start") LocalDateTime start,
                                @Param("end") LocalDateTime end);
}