package com.cuahangthucung.repository.use.Interface;

import com.cuahangthucung.entity.use.entity.YeuThich;
import com.cuahangthucung.entity.use.entity.YeuThichId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface YeuThichRepository extends JpaRepository<YeuThich, YeuThichId>,
        JpaSpecificationExecutor<YeuThich> {

    // Sửa thành Integer để khớp với User.userID
    List<YeuThich> findByIdMaUser(Integer maUser);

    // Sửa thành Integer
    boolean existsByIdMaUserAndIdMaSP(Integer maUser, String maSP);

    // Sửa thành Integer
    void deleteByIdMaUser(Integer maUser);

    // Sửa thành Integer
    long countByIdMaUser(Integer maUser);
}