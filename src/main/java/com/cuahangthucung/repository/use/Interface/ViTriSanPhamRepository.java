package com.cuahangthucung.repository.use.Interface;

import com.cuahangthucung.entity.use.entity.ViTriSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ViTriSanPhamRepository
        extends JpaRepository<ViTriSanPham,String> {

    boolean existsByViTri(String viTri);

    Optional<ViTriSanPham> findByViTri(String viTri);

    List<ViTriSanPham> findByViTriContainingIgnoreCase(String viTri);

    @Query("""
        SELECT COUNT(sp)
        FROM SanPham sp
        WHERE sp.viTriSanPham.maViTri=:ma
    """)
    Long countSanPham(@Param("ma") String ma);

    @Query("""
        SELECT v
        FROM ViTriSanPham v
        ORDER BY v.maViTri DESC
    """)
    List<ViTriSanPham> findAllDesc();


}