package org.detective.services.payment;


import org.detective.dto.PaymentRequestDTO;
import org.detective.dto.PaymentResponseDTO;
import org.detective.dto.PaymentTotalDTO;
import org.detective.entity.*;
import org.detective.repository.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final UserPointRepository userPointRepository;
    private final ClientRepository clientRepository;
    private final DetectiveRepository detectiveRepository;

    public PaymentService(PaymentRepository paymentRepository, UserRepository userRepository,
                          ClientRepository clientRepository, UserPointRepository userPointRepository, DetectiveRepository detectiveRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.userPointRepository = userPointRepository;
        this.clientRepository = clientRepository;
        this.detectiveRepository = detectiveRepository;
    }

    public Payment savePayment(PaymentRequestDTO paymentRequestDTO) {
        // 1. 이메일을 기반으로 사용자 조회
        User user = userRepository.findByEmail(paymentRequestDTO.getEmail());

        // 결제 내역 저장
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setImpUid(paymentRequestDTO.getImpUid());
        payment.setMerchantUid(paymentRequestDTO.getMerchantUid());
        payment.setPrice(paymentRequestDTO.getPrice());
        payment.setEmail(paymentRequestDTO.getEmail());
        payment.setPaymentDetails(paymentRequestDTO.getPaymentDetail());
        payment.setStatus(Payment.PaymentStatus.COMPLETED);
        payment.setPaymentMethod(Payment.PaymentMethod.credit_card);

        payment = paymentRepository.save(payment);

        // 사용자 조회 및 포인트 갱신
        updatePoint(user, payment.getPrice());

        return payment;
    }

    public void updatePoint(User user, Long paymentPrice) {
        if(user.getRole().equals("ROLE_USER")) {
            // 1. userid로 client를 조회
            Client client = clientRepository.findOptionalByUser(user)
                    .orElseThrow(() -> new RuntimeException("해당 사용자의 포인트 내역을 찾을 수 없습니다."));

            // 2. 포인트 갱신 (현재 포인트 + 충전 금액)
            Long currentPoints = client.getCurrentPoints();
            Long updatedPoints = currentPoints + paymentPrice;
            client.setCurrentPoints(updatedPoints);

            System.out.println("현재 포인트 : " + client.getCurrentPoints());

            // 3. 포인트 업데이트
            clientRepository.save(client);
        } else {
            // 1. userid로 detective 조회)
            Detective detective = detectiveRepository.findOptionalByUser(user)
                    .orElseThrow(() -> new RuntimeException("해당 사용자의 포인트 내역을 찾을 수 없습니다."));

            // 2. 포인트 갱신 (현재 포인트 + 충전 금액)
            Long currentPoints = detective.getCurrentPoints();
            Long updatedPoints = currentPoints + paymentPrice;
            detective.setCurrentPoints(updatedPoints);

            // 3. 포인트 업데이트
            detectiveRepository.save(detective);
        }

        // 4. 포인트 변동 내역 기록 (UserPoint 엔티티에 추가)
        UserPoint userPoint = new UserPoint();
        userPoint.setUser(user);  // User 설정
        userPoint.setPointChangeAmount(paymentPrice);
        userPoint.setPointUsingType(UserPoint.PointUsingType.CHARGE); // 충전 기록
        userPointRepository.save(userPoint);

    }

    public List<PaymentResponseDTO> getPaymentHistory(Long userId) {
        return paymentRepository.getPaymentDetailsByUserId(userId);
    }

    public List<PaymentTotalDTO> getTotalPriceByRolesAndDate() {
        return paymentRepository.getTotalPriceByRolesAndDate();
    }

}
