package com.cuahangthucung.service.pet;

import com.cuahangthucung.entity.pet.entity.Pet;
import com.cuahangthucung.service.base.BaseService;

public interface PetService extends BaseService<Pet, String> {
    // Hàm đặc thù riêng cho Pet
    String generateNextMaPet();
}