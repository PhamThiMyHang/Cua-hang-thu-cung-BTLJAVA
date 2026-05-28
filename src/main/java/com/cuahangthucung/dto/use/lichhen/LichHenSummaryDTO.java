package com.cuahangthucung.dto.use.lichhen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LichHenSummaryDTO {
    private Long tongSoLichHen;
    private Long soPending;
    private Long soConfirmed;
    private Long soInProgress;
    private Long soDone;
    private Long soCancel;
}