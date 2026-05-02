package com.cuahangthucung.repository.pet;

import com.cuahangthucung.entity.pet.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, String>, JpaSpecificationExecutor<Pet> {
    // Giữ lại hàm đặc thù này để sinh mã tự động
    @Query("SELECT p FROM Pet p WHERE p.maPet LIKE :prefix% ORDER BY p.maPet DESC LIMIT 1")
    Optional<Pet> findLastPetByPrefix(@Param("prefix") String prefix);

    // TẤT CẢ các hàm findBy... khác có thể xóa bỏ nếu bạn dùng Specification
}