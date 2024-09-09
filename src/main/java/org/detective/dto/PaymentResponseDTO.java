package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO { // 결제 완료 후, 서버가 전달받는 데이터
    private String merchantUid;
    private String paymentDetails;
    private String paymentAt;
    private Long price;
}
