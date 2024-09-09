package org.detective.controller.payment;

import org.detective.dto.PaymentRequestDTO;
import org.detective.dto.PaymentResponseDTO;
import org.detective.entity.Payment;
import org.detective.entity.User;
import org.detective.services.member.UserService;
import org.detective.services.payment.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;

    public PaymentController(PaymentService paymentService, UserService userService) {
        this.paymentService = paymentService;
        this.userService = userService;
    }

    @PostMapping("/charge")
    public ResponseEntity<?> savePayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        try {
            System.out.println("payment : " + paymentRequestDTO);

            // 결제 정보를 저장하는 서비스 호출
            Payment payment = paymentService.savePayment(paymentRequestDTO);

            System.out.println("결제 정보를 저장 payment : " + paymentRequestDTO);


            // 저장된 결제 정보를 바탕으로 응답 DTO 생성
            PaymentResponseDTO responseDTO = new PaymentResponseDTO(
                    payment.getMerchantUid(),
                    payment.getPaymentDetails(),
                    payment.getPaymentAt(),
                    payment.getPrice()
            );

            System.out.println("저장된 결제 정보를 return : " + responseDTO);

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("결제 처리 중 오류 발생: " + e.getMessage());
        }
    }

}