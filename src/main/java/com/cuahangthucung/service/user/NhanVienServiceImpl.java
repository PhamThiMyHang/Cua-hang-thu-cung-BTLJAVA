package com.cuahangthucung.service.user;

import com.cuahangthucung.entity.user.entity.NhanVien;
import com.cuahangthucung.repository.user.NhanVienRepository;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class NhanVienServiceImpl extends BaseServiceImpl<NhanVien, Integer, NhanVienRepository> implements NhanVienService {

    public NhanVienServiceImpl(NhanVienRepository repository) {
        super(repository);
    }

    @Override
    public Optional<NhanVien> findByUserUserID(Integer userId) {
        return repository.findByUserUserID(userId);
    }

    @Override
    public Optional<NhanVien> findBySdt(String sdt) {
        return repository.findBySdt(sdt);
    }

    @Override
    public boolean existsBySdt(String sdt) {
        return repository.existsBySdt(sdt);
    }

    @Override
    public String generateNextMaNV() {
        String prefix = "NV" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyMM"));
        
        return repository.findLastNhanVienByPrefix(prefix)
                .map(last -> {
                    String lastMa = last.getMaNV().toString(); // giả sử MaNV là Integer, cần convert
                    // Hoặc bạn có thể thay đổi query để trả về String nếu cần
                    int lastNumber = Integer.parseInt(lastMa.substring(6));
                    return String.format("%s%03d", prefix, lastNumber + 1);
                })
                .orElse(prefix + "001");
    }
}