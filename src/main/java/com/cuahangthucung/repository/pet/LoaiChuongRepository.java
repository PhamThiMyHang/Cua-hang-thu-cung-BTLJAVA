package com.cuahangthucung.repository.pet;

import com.cuahangthucung.entity.pet.entity.LoaiChuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiChuongRepository extends JpaRepository<LoaiChuong, String>, JpaSpecificationExecutor<LoaiChuong> {
    // Không cần viết thêm hàm nào ở đây nữa!
}