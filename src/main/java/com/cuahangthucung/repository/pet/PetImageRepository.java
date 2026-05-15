
    //1. Tìm thông tin về một bản ghi cs maImg được truyền vào
    //2. Tìm tất cả bản ghi của một pet theo ma được nhập vào
    //3. Tìm tất cả bản ghi của các pet trong ngày nhập vaoof

    // 5. Tìm ban ghii cua 1 pet co ma cu the vào 1 ngay cụ thẻ
    //6. Tìm ban ghi cua 1 pet co ma cu the,  ngay cu the
    // các hàm count
    //2. tính số bản ghi  của một pet theo ma được nhập vào
    //3. tính số bản ghicủa các pet trong ngày nhập vaoof
    // 5.  tính số bản ghi  cua 1 pet co ma cu the vào 1 ngay cụ thẻ
package com.cuahangthucung.repository.pet;

import com.cuahangthucung.entity.pet.entity.PetImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
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