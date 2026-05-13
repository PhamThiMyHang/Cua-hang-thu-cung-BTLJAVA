package com.cuahangthucung.service.pet;

import com.cuahangthucung.entity.pet.entity.Chuong;
import com.cuahangthucung.repository.pet.ChuongRepository;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ChuongServiceImpl extends BaseServiceImpl<Chuong, String, ChuongRepository>
        implements ChuongService {
    public ChuongServiceImpl(ChuongRepository repository) { super(repository); }
}