package com.cuahangthucung.service.user;

import com.cuahangthucung.entity.user.entity.ChamCong;
import com.cuahangthucung.repository.user.ChamCongRepository;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ChamCongServiceImpl extends BaseServiceImpl<ChamCong, Integer, ChamCongRepository>
        implements ChamCongService {

    public ChamCongServiceImpl(ChamCongRepository repository) {
        super(repository);
    }

    @Override
    public Optional<ChamCong> findByNhanVienMaNVAndNgay(Integer maNV, LocalDate ngay) {
        return repository.findByNhanVienMaNVAndNgay(maNV, ngay);
    }

    @Override
    public List<ChamCong> findByNhanVienMaNV(Integer maNV) {
        return repository.findByNhanVienMaNV(maNV);
    }

    @Override
    public List<ChamCong> findByNgayBetween(LocalDate fromDate, LocalDate toDate) {
        return repository.findByNgayBetween(fromDate, toDate);
    }
}