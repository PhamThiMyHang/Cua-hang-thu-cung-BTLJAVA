package com.cuahangthucung.service.use.serviceImpl;

import com.cuahangthucung.dto.use.ViTriSanPham.ViTriSanPhamDTO;
import com.cuahangthucung.dto.use.ViTriSanPham.ViTriSanPhamRequest;
import com.cuahangthucung.entity.use.entity.ViTriSanPham;
import com.cuahangthucung.exception.ResourceNotFoundException;
import com.cuahangthucung.repository.use.Interface.SanPhamRepository;
import com.cuahangthucung.repository.use.Interface.ViTriSanPhamRepository;
import com.cuahangthucung.service.use.service.ViTriSanPhamService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ViTriSanPhamServiceImpl implements ViTriSanPhamService {

    private final ViTriSanPhamRepository repository;
    private final SanPhamRepository sanPhamRepository;

    public ViTriSanPhamServiceImpl(
            ViTriSanPhamRepository repository,
            SanPhamRepository sanPhamRepository) {

        this.repository = repository;
        this.sanPhamRepository = sanPhamRepository;
    }

    @Override
    public List<ViTriSanPhamDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::convert)
                .toList();
    }

    @Override
    public ViTriSanPhamDTO findById(String maViTri) {

        return repository.findById(maViTri)
                .map(this::convert)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy vị trí: " + maViTri
                        ));
    }

    @Override
    @Transactional
    public ViTriSanPhamDTO create(ViTriSanPhamRequest request) {

        if(repository.existsById(request.getMaViTri())){

            throw new IllegalArgumentException(
                    "Mã vị trí đã tồn tại");
        }

        if(repository.existsByViTri(
                request.getViTri())){

            throw new IllegalArgumentException(
                    "Tên vị trí đã tồn tại");
        }

        ViTriSanPham entity = new ViTriSanPham();

        entity.setMaViTri(request.getMaViTri());
        entity.setViTri(request.getViTri());

        return convert(repository.save(entity));
    }

    @Override
    @Transactional
    public ViTriSanPhamDTO update(
            String maViTri,
            ViTriSanPhamRequest request) {

        ViTriSanPham entity = repository.findById(maViTri)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy vị trí: " + maViTri
                        ));

        // Không sửa mã vị trí
        Optional<ViTriSanPham> old=
                repository.findByViTri(
                        request.getViTri());

        if(old.isPresent()
                &&!old.get().getMaViTri()
                .equals(maViTri)){

            throw new IllegalArgumentException(
                    "Tên vị trí đã tồn tại");
        }
        entity.setViTri(request.getViTri());

        return convert(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(String maViTri) {

        ViTriSanPham entity = repository.findById(maViTri)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy vị trí: " + maViTri
                        ));

        long count = sanPhamRepository
                .countByViTriSanPham_MaViTri(maViTri);

        if (count > 0) {
            throw new IllegalArgumentException(
                    "Không thể xóa vị trí đang chứa sản phẩm."
            );
        }

        repository.delete(entity);
    }

    private ViTriSanPhamDTO convert(ViTriSanPham entity) {

        return new ViTriSanPhamDTO(
                entity.getMaViTri(),
                entity.getViTri()
        );
    }
}