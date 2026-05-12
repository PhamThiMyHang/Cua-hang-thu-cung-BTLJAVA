package com.cuahangthucung.service.pet;

import com.cuahangthucung.entity.pet.entity.Pet;
import com.cuahangthucung.repository.pet.PetRepository;
import com.cuahangthucung.service.base.BaseServiceImpl;
import com.cuahangthucung.service.pet.PetService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PetServiceImpl extends BaseServiceImpl<Pet, String, PetRepository> implements PetService {

    public PetServiceImpl(PetRepository repository) {
        super(repository);
    }

    @Override
    public String generateNextMaPet() {
        // 1. Lấy Prefix: P + YY + MM (Ví dụ: P2605)
        String prefix = "P" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyMM"));

        // 2. Tìm mã Pet cuối cùng có prefix này trong DB
        return repository.findLastPetByPrefix(prefix)
                .map(lastPet -> {
                    String lastMa = lastPet.getMaPet(); // P260501
                    int lastNumber = Integer.parseInt(lastMa.substring(5)); // Lấy "01" -> 1
                    return String.format("%s%02d", prefix, lastNumber + 1); // Trả về P260502
                })
                .orElse(prefix + "01"); // Nếu tháng này chưa có pet nào, trả về P260501
    }

    @Override
    public Pet save(Pet entity) {
        if (entity.getMaPet() == null || entity.getMaPet().isEmpty()) {
            entity.setMaPet(generateNextMaPet());
        }
        return super.save(entity);
    }
}