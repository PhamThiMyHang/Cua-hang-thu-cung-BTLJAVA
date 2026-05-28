package com.cuahangthucung.service.use.serviceImpl;

import com.cuahangthucung.dto.use.chitietdonhang.ChiTietDonHangDTO;
import com.cuahangthucung.dto.use.chitietdonhang.ChiTietDonHangRequest;
import com.cuahangthucung.entity.use.entity.ChiTietDonHang;
import com.cuahangthucung.entity.use.entity.ChiTietDonHangId;
import com.cuahangthucung.entity.use.entity.DonHang;
import com.cuahangthucung.entity.use.entity.SanPham;
import com.cuahangthucung.repository.use.Interface.ChiTietDonHangRepository;
import com.cuahangthucung.service.base.BaseServiceImpl;
import com.cuahangthucung.service.use.service.ChiTietDonHangService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChiTietDonHangServiceImpl extends BaseServiceImpl<ChiTietDonHang, ChiTietDonHangId, ChiTietDonHangRepository>
        implements ChiTietDonHangService {

    public ChiTietDonHangServiceImpl(ChiTietDonHangRepository repository) {
        super(repository);
    }

    @Override
    public List<ChiTietDonHangDTO> findByMaDH(String maDH) {
        return repository.findByDonHang_MaDH(maDH)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChiTietDonHangDTO> findByMaSP(String maSP) {
        return repository.findBySanPham_MaSP(maSP)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ChiTietDonHangDTO saveRequest(ChiTietDonHangRequest request) {
        // Khởi tạo đối tượng định danh IdClass để truy vấn tìm kiếm dữ liệu cũ nếu có
        ChiTietDonHangId id = new ChiTietDonHangId();
        id.setDonHang(request.getMaDH());
        id.setSanPham(request.getMaSP());

        ChiTietDonHang ct = repository.findById(id).orElse(new ChiTietDonHang());

        // Thay vì ct.setId(id), ta gán trực tiếp các đối tượng proxy chứa ID
        if (ct.getDonHang() == null) {
            DonHang dh = new DonHang();
            dh.setMaDH(request.getMaDH());
            ct.setDonHang(dh);
        }
        if (ct.getSanPham() == null) {
            SanPham sp = new SanPham();
            sp.setMaSP(request.getMaSP());
            ct.setSanPham(sp);
        }

        ct.setSoLuong(request.getSoLuong());
        ct.setDonGia(request.getDonGia());

        return convertToDTO(repository.save(ct));
    }

    @Override
    public ChiTietDonHangDTO findByIdDTO(String maDH, String maSP) {
        ChiTietDonHangId id = new ChiTietDonHangId();
        id.setDonHang(maDH);
        id.setSanPham(maSP);
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy chi tiết đơn hàng: maDH=" + maDH + ", maSP=" + maSP));
    }

    @Override
    @Transactional
    public void deleteByMaDH(String maDH) {
        repository.deleteByDonHang_MaDH(maDH);
    }

    @Override
    public BigDecimal tinhTongTien(String maDH) {
        BigDecimal tongTien = repository.tinhTongTienDonHang(maDH);
        return (tongTien != null) ? tongTien : BigDecimal.ZERO;
    }

    // ──────────────────────────────────────────
    // Helper: Entity → DTO
    // ──────────────────────────────────────────
    private ChiTietDonHangDTO convertToDTO(ChiTietDonHang entity) {
        if (entity == null) return null;

        ChiTietDonHangDTO dto = new ChiTietDonHangDTO();
        BeanUtils.copyProperties(entity, dto);

        // Đọc trực tiếp từ các thực thể liên kết thay vì dùng entity.getId()
        if (entity.getDonHang() != null) {
            dto.setMaDH(entity.getDonHang().getMaDH());
            dto.setNgayTao(entity.getDonHang().getNgayTao());
            dto.setTrangThaiDonHang(entity.getDonHang().getTrangThai().name());
        }
        if (entity.getSanPham() != null) {
            dto.setMaSP(entity.getSanPham().getMaSP());
            dto.setTenSP(entity.getSanPham().getTenSP());
        }

        // Tự động tính toán thành tiền
        if (entity.getSoLuong() != null && entity.getDonGia() != null) {
            BigDecimal soLuongBd = BigDecimal.valueOf(entity.getSoLuong());
            dto.setThanhTien(entity.getDonGia().multiply(soLuongBd));
        }

        return dto;
    }
}