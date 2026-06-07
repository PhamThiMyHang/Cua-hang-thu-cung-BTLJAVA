package com.cuahangthucung.repository.use.Interface;

import com.cuahangthucung.entity.use.entity.YeuThich;
import com.cuahangthucung.entity.use.entity.YeuThichId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    // Trong YeuThichRepository.java
    long countByIdMaSP(String maSP);

    // Trong YeuThichRepository.java
    @Query("SELECT y.id.maSP FROM YeuThich y WHERE y.id.maUser = :maUser")
    List<String> findListMaSPByMaUser(@Param("maUser") Integer maUser);

    // Trong YeuThichRepository.java
    @Query("SELECT y FROM YeuThich y JOIN FETCH y.sanPham WHERE y.id.maUser = :maUser")
    List<YeuThich> findByMaUserWithSanPham(@Param("maUser") Integer maUser);

    @Query("SELECT y FROM YeuThich y JOIN FETCH y.user WHERE y.id.maSP = :maSP")
    List<YeuThich> findByMaSP(@Param("maSP") String maSP);
}