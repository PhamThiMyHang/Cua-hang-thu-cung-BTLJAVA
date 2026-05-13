package com.cuahangthucung.service.user;

import com.cuahangthucung.entity.user.entity.LichSuDangNhap;
import com.cuahangthucung.repository.user.LichSuDangNhapRepository;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LichSuDangNhapServiceImpl extends BaseServiceImpl<LichSuDangNhap, Integer, LichSuDangNhapRepository>
        implements LichSuDangNhapService {

    public LichSuDangNhapServiceImpl(LichSuDangNhapRepository repository) {
        super(repository);
    }

    @Override
    public List<LichSuDangNhap> findByUserUserID(Integer userId) {
        return repository.findByUserUserID(userId);
    }

    @Override
    public List<LichSuDangNhap> findTop10ByUserUserIDOrderByThoiGianDesc(Integer userId) {
        return repository.findTop10ByUserUserIDOrderByThoiGianDesc(userId);
    }
}