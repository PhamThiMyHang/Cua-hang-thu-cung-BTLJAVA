package com.cuahangthucung.repository.pet;

import com.cuahangthucung.dto.pet.PetSearchRequest;
import com.cuahangthucung.entity.pet.entity.Pet;
import com.cuahangthucung.repository.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;
import java.math.BigDecimal;

public class PetSpecification {

    public static Specification<Pet> getFilter(PetSearchRequest request) {
        if (request == null) return null;

        // Sử dụng Specification.allOf() thay cho where() hoặc allOn()
        // Nó sẽ kết hợp tất cả các Specification bên trong bằng phép AND
        return Specification.allOf(
                GenericSpecification.fieldContains("tenPet", request.getTenPet()),
                GenericSpecification.fieldEquals("giong", request.getGiong()),
                GenericSpecification.fieldEquals("tinhTrang", request.getTinhTrang()),
                GenericSpecification.fieldEquals("chuong.maChuong", request.getMaChuong()),
                GenericSpecification.<Pet, BigDecimal>fieldBetween("gia", request.getGiaMin(), request.getGiaMax())
        );
    }
}