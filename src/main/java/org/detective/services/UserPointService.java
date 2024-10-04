package org.detective.services;

import lombok.RequiredArgsConstructor;
import org.detective.entity.ChatRoom;
import org.detective.entity.Client;
import org.detective.entity.User;
import org.detective.entity.UserPoint;
import org.detective.repository.ChatRoomRepository;
import org.detective.repository.ClientRepository;
import org.detective.repository.UserPointRepository;
import org.detective.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserPointService {

    private final UserPointRepository userPointRepository;
    private final ClientRepository clientRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public void usePoints(Long userId, Long pointsToUse, String chatRoomId){
        Client client = clientRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("Client not found"));

        client.usePoints(pointsToUse);
        clientRepository.save(client);

        UserPoint userPoint = UserPoint.builder()
                .user(client.getUser())
                .pointUsingType(UserPoint.PointUsingType.USE)
                .pointChangeAmount(-pointsToUse)
                .build();
        userPointRepository.save(userPoint);

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));
        chatRoom.enableUnlimitedAccess();
        chatRoomRepository.save(chatRoom);

    }

    // 특정 사용자의 포인트 내역을 가져오는 서비스 메서드
    public List<UserPoint> getUserPointsByUserId(Long userId) {
        return userPointRepository.findByUserUserId(userId);
    }

}
