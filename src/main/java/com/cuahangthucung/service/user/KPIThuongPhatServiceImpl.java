package com.cuahangthucung.service.user;

import com.cuahangthucung.entity.user.entity.KPIThuongPhat;
import com.cuahangthucung.repository.user.KPIThuongPhatRepository;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KPIThuongPhatServiceImpl extends BaseServiceImpl<KPIThuongPhat, Integer, KPIThuongPhatRepository>
        implements KPIThuongPhatService {

    public KPIThuongPhatServiceImpl(KPIThuongPhatRepository repository) {
        super(repository);
    }

    @Override
    public List<KPIThuongPhat> findByNhanVienMaNV(Integer maNV) {
        return repository.findByNhanVienMaNV(maNV);
    }

    @Override
    public List<KPIThuongPhat> findByNhanVienMaNVAndThang(Integer maNV, String thang) {
        return repository.findByNhanVienMaNVAndThang(maNV, thang);
    }
}