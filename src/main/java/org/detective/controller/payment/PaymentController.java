package org.detective.controller.payment;

import org.detective.controller.Member.MemberController;
import org.detective.dto.PaymentRequestDTO;
import org.detective.dto.PaymentResponseDTO;
import org.detective.entity.Payment;
import org.detective.entity.User;
import org.detective.entity.UserPoint;
import org.detective.services.UserPointService;
import org.detective.services.member.UserService;
import org.detective.services.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;
    private final MemberController memberController;

    public PaymentController(PaymentService paymentService, UserService userService, MemberController memberController) {
        this.paymentService = paymentService;
        this.userService = userService;
        this.memberController = memberController;
    }

    @Autowired
    private UserPointService userPointService;

    // 결제 내역 저장, 포인트 사용 내역, 현재 포인트 업데이트
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


    // 전체 결제 내역
    @GetMapping("/history")
    public List<PaymentResponseDTO> getPaymentHistory() {
        Long userId = memberController.getUserInfo().getUserId();  // 사용자정보
        System.out.println("getPaymentHistory user : " + userId);

        List<PaymentResponseDTO> paymentHistory = paymentService.getPaymentHistory(userId);

        return paymentHistory;
    }

    // 특정 사용자의 포인트 내역 조회
    @GetMapping("/points/{userId}")
    public ResponseEntity<List<UserPoint>> getUserPoints(@PathVariable Long userId) {
        List<UserPoint> userPoints = userPointService.getUserPointsByUserId(userId);
        System.out.println("pointhistory"+userPoints);
        return ResponseEntity.ok(userPoints);
    }
}