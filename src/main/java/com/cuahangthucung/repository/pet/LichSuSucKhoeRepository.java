
    //1. Tìm thông tin về một bản ghi cs maLS được truyền vào
    //2. Tìm tất cả lịch sử sức khỏe của một pet theo ma được nhập vào
    //3. Tìm tất cả lich sử sức khỏe của các pet trong ngày nhập vaoof
    //4.. Tìm tất cả lịch sử sức khoe ca ket luận l loai đươc nhap vao (column loai)
    // 5. Tìm lich su cua 1 pet co ma cu the vào 1 ngay cụ thẻ
    //6. Tìm lich su cua 1 pet co ma cu the, loai cu the, ngay cu the
    // 7. Tìm lich su cua 1 pet co ma cu the, loai cu the,
    // 8. Tìm lich su cua  loai cu the, ngay cu the
    // các hàm count
    //2. tính số bản ghi lịch sử sức khỏe của một pet theo ma được nhập vào
    //3. tính số bản ghi lich sử sức khỏe của các pet trong ngày nhập vaoof
    //4.. tính số bản ghi lịch sử sức khoe ca ket luận l loai đươc nhap vao (column loai)
    // 5.  tính số bản ghi lich su cua 1 pet co ma cu the vào 1 ngay cụ thẻ
    //6.  tính số bản ghi lich su cua 1 pet co ma cu the, loai cu the, ngay cu the
    // 7. tính số bản ghi lich su cua 1 pet co ma cu the, loai cu the,
    // 8.  tính số bản ghi lich su cua  loai cu the, ngay cu the
package com.cuahangthucung.repository.pet;

import com.cuahangthucung.entity.pet.entity.LichSuSucKhoe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LichSuSucKhoeRepository extends JpaRepository<LichSuSucKhoe, Integer>, JpaSpecificationExecutor<LichSuSucKhoe> {
    // Không cần viết thêm bất cứ hàm nào.
    // JpaSpecificationExecutor đã cung cấp sẵn:
    // - findAll(Specification) cho các yêu cầu tìm kiếm 1-8
    // - count(Specification) cho các yêu cầu tính toán 2-8
}