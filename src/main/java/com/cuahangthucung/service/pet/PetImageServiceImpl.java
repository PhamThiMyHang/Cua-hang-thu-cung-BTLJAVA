package com.cuahangthucung.service.pet;

import com.cuahangthucung.entity.pet.entity.PetImage;
import com.cuahangthucung.repository.pet.PetImageRepository;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PetImageServiceImpl extends BaseServiceImpl<PetImage, Integer, PetImageRepository>
        implements PetImageService {
    public PetImageServiceImpl(PetImageRepository repository) { super(repository); }
}