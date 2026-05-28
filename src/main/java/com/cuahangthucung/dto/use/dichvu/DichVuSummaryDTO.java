package com.cuahangthucung.dto.use.dichvu;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DichVuSummaryDTO {
    // Sửa thành Long để khớp với kết quả hàm COUNT trong JPA
    private Long tongSoLuongDichVu;
    private BigDecimal giaThapNhat;
    private BigDecimal giaCaoNhat;
    private BigDecimal giaTrungBinh;
}