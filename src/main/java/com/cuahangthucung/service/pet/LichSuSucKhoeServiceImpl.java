package com.cuahangthucung.service.pet;

import com.cuahangthucung.entity.pet.entity.LichSuSucKhoe;
import com.cuahangthucung.repository.pet.LichSuSucKhoeRepository;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class LichSuSucKhoeServiceImpl extends BaseServiceImpl<LichSuSucKhoe, Integer, LichSuSucKhoeRepository>
        implements LichSuSucKhoeService {
    public LichSuSucKhoeServiceImpl(LichSuSucKhoeRepository repository) { super(repository); }
}