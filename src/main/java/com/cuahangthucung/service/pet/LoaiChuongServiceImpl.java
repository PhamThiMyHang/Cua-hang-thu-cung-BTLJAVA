package com.cuahangthucung.service.pet;

import com.cuahangthucung.entity.pet.entity.LoaiChuong;
import com.cuahangthucung.repository.pet.LoaiChuongRepository;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class LoaiChuongServiceImpl extends BaseServiceImpl<LoaiChuong, String, LoaiChuongRepository>
        implements LoaiChuongService {
    public LoaiChuongServiceImpl(LoaiChuongRepository repository) { super(repository); }
}