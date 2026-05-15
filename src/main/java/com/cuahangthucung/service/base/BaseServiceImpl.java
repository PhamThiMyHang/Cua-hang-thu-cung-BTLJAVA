package com.cuahangthucung.service.base;

import com.cuahangthucung.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Triển khai các hàm dùng chung để tối ưu code.
 * R là Repository, phải hỗ trợ cả JpaRepository và JpaSpecificationExecutor
 */
public abstract class BaseServiceImpl<T, ID, R extends JpaRepository<T, ID> & JpaSpecificationExecutor<T>>
        implements BaseService<T, ID> {

    protected final R repository;

    protected BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public T update(ID id, T entity) {
        // Kiểm tra xem ID có tồn tại không trước khi update
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy bản ghi với ID: " + id);
        }
        // Lưu ý: Logic cụ thể của update thường sẽ được ghi đè (Override)
        // ở các Service con nếu cần xử lý phức tạp hơn
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(ID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy bản ghi để xóa với ID: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public long count(Specification<T> spec) {
        return repository.count(spec);
    }

    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }
}