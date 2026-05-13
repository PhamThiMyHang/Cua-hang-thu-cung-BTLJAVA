package com.cuahangthucung.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

/**
 * Interface chung cho các Service
 * @param <T> Kiểu Entity (ví dụ: Pet, Chuong)
 * @param <ID> Kiểu dữ liệu khóa chính (ví dụ: String, Integer)
 */
public interface BaseService<T, ID> {

    // Lấy tất cả bản ghi
    List<T> findAll();

    // Lấy tất cả có phân trang và bộ lọc nâng cao (Specification)
    Page<T> findAll(Specification<T> spec, Pageable pageable);

    // Tìm một bản ghi theo ID
    Optional<T> findById(ID id);

    // Lưu mới bản ghi
    T save(T entity);

    // Cập nhật bản ghi
    T update(ID id, T entity);

    // Xóa bản ghi
    void deleteById(ID id);

    // Đếm số lượng theo bộ lọc
    long count(Specification<T> spec);

    // Kiểm tra tồn tại
    boolean existsById(ID id);
}