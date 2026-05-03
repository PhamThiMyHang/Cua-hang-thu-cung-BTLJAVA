
    //1. Tìm thông tin về một bản ghi cs maImg được truyền vào
    //2. Tìm tất cả bản ghi của một pet theo ma được nhập vào
    //3. Tìm tất cả bản ghi của các pet trong ngày nhập vaoof

    // 5. Tìm ban ghii cua 1 pet co ma cu the vào 1 ngay cụ thẻ
    //6. Tìm ban ghi cua 1 pet co ma cu the,  ngay cu the
    // các hàm count
    //2. tính số bản ghi  của một pet theo ma được nhập vào
    //3. tính số bản ghicủa các pet trong ngày nhập vaoof
    // 5.  tính số bản ghi  cua 1 pet co ma cu the vào 1 ngay cụ thẻ
package com.cuahangthucung.repository.pet;

import com.cuahangthucung.entity.pet.entity.PetImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PetImageRepository extends JpaRepository<PetImage, Integer>, JpaSpecificationExecutor<PetImage> {
    // Toàn bộ 5 yêu cầu tìm kiếm và 3 yêu cầu count sẽ được xử lý qua Specification
}