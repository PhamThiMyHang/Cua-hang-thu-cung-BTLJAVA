package com.cuahangthucung.service.user;

import com.cuahangthucung.entity.user.entity.LichTruc;
import com.cuahangthucung.repository.user.LichTrucRepository;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LichTrucServiceImpl extends BaseServiceImpl<LichTruc, Integer, LichTrucRepository>
        implements LichTrucService {

    public LichTrucServiceImpl(LichTrucRepository repository) {
        super(repository);
    }

    @Override
    public List<LichTruc> findByNhanVienMaNV(Integer maNV) {
        return repository.findByNhanVienMaNV(maNV);
    }

    @Override
    public List<LichTruc> findByNhanVienMaNVAndNgay(Integer maNV, LocalDate ngay) {
        return repository.findByNhanVienMaNVAndNgay(maNV, ngay);
    }

    @Override
    public List<LichTruc> findByNgayBetween(LocalDate fromDate, LocalDate toDate) {
        return repository.findByNgayBetween(fromDate, toDate);
    }
}