package com.cuahangthucung.service.user;

import com.cuahangthucung.entity.user.entity.KhachHang;
import com.cuahangthucung.repository.user.KhachHangRepository;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class KhachHangServiceImpl extends BaseServiceImpl<KhachHang, Integer, KhachHangRepository> implements KhachHangService {

    public KhachHangServiceImpl(KhachHangRepository repository) {
        super(repository);
    }

    @Override
    public Optional<KhachHang> findByUserUserID(Integer userId) {
        return repository.findByUserUserID(userId);
    }

    @Override
    public Optional<KhachHang> findBySdt(String sdt) {
        return repository.findBySdt(sdt);
    }

    @Override
    public boolean existsBySdt(String sdt) {
        return repository.existsBySdt(sdt);
    }
/*
    @Override
    public String generateNextMaKH() {
        String prefix = "KH" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyMM"));
        
        return repository.findLastKhachHangByPrefix(prefix)
                .map(last -> {
                    String lastMa = last.getMaKH().toString();
                    int lastNumber = Integer.parseInt(lastMa.substring(6));
                    return String.format("%s%03d", prefix, lastNumber + 1);
                })
                .orElse(prefix + "001");
    }


 */
    @Override
    public String generateNextMaKH() {
        // Nếu bạn vẫn muốn trả về String cho Frontend hiển thị,
        // chỉ cần lấy ID lớn nhất hiện tại cộng thêm 1.
        return repository.findLastKhachHang()
                .map(last -> String.valueOf(last.getMaKH() + 1))
                .orElse("1"); // Nếu chưa có khách nào thì bắt đầu từ 1
    }
}