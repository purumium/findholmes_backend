package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {
    private String impUid;
    private String merchantUid;
    private Long price;
    private String email;
    private String paymentDetail;
}
