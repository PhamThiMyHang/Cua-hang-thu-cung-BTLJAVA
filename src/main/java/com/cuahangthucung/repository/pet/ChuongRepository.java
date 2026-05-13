
package com.cuahangthucung.repository.pet;

import com.cuahangthucung.entity.pet.entity.Chuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ChuongRepository extends JpaRepository<Chuong, String>, JpaSpecificationExecutor<Chuong> {
    // Tất cả các chức năng tìm kiếm (1, 2, 3) và tính toán (4, 5)
    // đều sẽ được xử lý thông qua Specification tại tầng Service.
}
