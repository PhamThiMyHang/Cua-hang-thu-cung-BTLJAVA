package com.cuahangthucung.service.user;

import com.cuahangthucung.entity.user.entity.HoSoNhanVien;
import com.cuahangthucung.repository.user.HoSoNhanVienRepository;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HoSoNhanVienServiceImpl extends BaseServiceImpl<HoSoNhanVien, Integer, HoSoNhanVienRepository>
        implements HoSoNhanVienService {

    public HoSoNhanVienServiceImpl(HoSoNhanVienRepository repository) {
        super(repository);
    }

    @Override
    public Optional<HoSoNhanVien> findByNhanVienMaNV(Integer maNV) {
        return repository.findByNhanVienMaNV(maNV);
    }

    @Override
    public boolean existsByNhanVienMaNV(Integer maNV) {
        return repository.existsByNhanVienMaNV(maNV);
    }
}