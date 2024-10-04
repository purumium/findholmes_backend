package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTotalDTO {
    private String date;                 // 날짜
    private Long userTotalPrice;         // ROLE_USER의 총합
    private Long detectiveTotalPrice;    // ROLE_DETECTIVE의 총합
}
