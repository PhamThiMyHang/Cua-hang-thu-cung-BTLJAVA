package com.cuahangthucung.service.user;

import com.cuahangthucung.entity.user.entity.NhanVien;
import com.cuahangthucung.repository.user.NhanVienRepository;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class NhanVienServiceImpl extends BaseServiceImpl<NhanVien, Integer, NhanVienRepository> implements NhanVienService {

    public NhanVienServiceImpl(NhanVienRepository repository) {
        super(repository);
    }

    @Override
    public Optional<NhanVien> findByUserUserID(Integer userId) {
        return repository.findByUserUserID(userId);
    }

    @Override
    public Optional<NhanVien> findBySdt(String sdt) {
        return repository.findBySdt(sdt);
    }

    @Override
    public boolean existsBySdt(String sdt) {
        return repository.existsBySdt(sdt);
    }

    @Override
    public String generateNextMaNV() {
        // Chỉ đơn giản là lấy ID cuối + 1
        return repository.findLastNhanVien()
                .map(last -> String.valueOf(last.getMaNV() + 1))
                .orElse("1"); // Nếu chưa có nv nào thì bắt đầu từ 1
    }
}