package org.detective.services.chat;

import lombok.RequiredArgsConstructor;
import org.detective.dto.ChatRoomDTO;
import org.detective.dto.ParticipantDTO;
import org.detective.entity.ChatRoom;
import org.detective.entity.Estimate;
import org.detective.entity.User;
import org.detective.repository.ChatRoomRepository;
import org.detective.repository.EstimateRepository;
import org.detective.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final EstimateRepository estimateRepository;
    private final UserRepository userRepository;

    @Transactional
    public ChatRoom createChatRoom(Long estimateId, Long userId) {
        Estimate estimate = estimateRepository.findById(estimateId)
                .orElseThrow(() -> new IllegalArgumentException("estimate를 찾을 수 없습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("client를 찾을 수 없습니다."));

        Long clientId = user.getUserId();
        Long detectiveId = estimate.getDetective().getUser().getUserId();

        List<ChatRoom.Participant> participants = List.of(
                new ChatRoom.Participant(clientId, "c"),
                new ChatRoom.Participant(detectiveId, "d")
        );

        // ChatRoom 생성
        ChatRoom chatRoom = ChatRoom.builder()
                .estimateId(estimateId)
                .participants(participants)
                .createdAt(LocalDateTime.now())
                .build();
        return chatRoomRepository.save(chatRoom);
    }

    @Transactional
    public List<ChatRoomDTO> findByChatList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("client를 찾을 수 없습니다."));
        String role = user.getRole();
        List<ChatRoom> chatRooms;

        if(role.equals("ROLE_USER")) {
            chatRooms = chatRoomRepository.findByUserIdAndRoleIsClient(userId);
            return chatRooms.stream()
                    .map(chatRoom -> {
                        // participants에서 userId로 username 조회
                        List<ParticipantDTO> participants = chatRoom.getParticipants().stream()
                                .map(participant -> {
                                    User detective = userRepository.findById(participant.getUserId())
                                            .orElseThrow(() -> new IllegalArgumentException("상대 탐정을 찾을 수 없습니다."));
                                    return new ParticipantDTO(detective.getUserName(), participant.getRole());
                                })
                                .collect(Collectors.toList());

                        return new ChatRoomDTO(chatRoom.getId(), participants, chatRoom.getCreatedAt());
                    })
                    .collect(Collectors.toList());

            // ROLE_DETECTIVE이면 role이 "c"인 참여자만 반환
        } else if (role.equals("ROLE_DETECTIVE")) {
            chatRooms = chatRoomRepository.findByUserIdAndRoleIsDetective(userId); // 이미 "d"로 필터된 결과 가져오기
            return chatRooms.stream()
                    .map(chatRoom -> {
                        // participants에서 userId로 username 조회
                        List<ParticipantDTO> participants = chatRoom.getParticipants().stream()
                                .map(participant -> {
                                    User client = userRepository.findById(participant.getUserId())
                                            .orElseThrow(() -> new IllegalArgumentException("상대 의뢰인을 찾을 수 없습니다."));
                                    return new ParticipantDTO(client.getUserName(), participant.getRole());
                                })
                                .collect(Collectors.toList());

                        return new ChatRoomDTO(chatRoom.getId(), participants, chatRoom.getCreatedAt());
                    })
                    .collect(Collectors.toList());

        } else {
            throw new IllegalArgumentException("잘못된 역할입니다.");
        }
    }

    @Transactional
    public Optional<ChatRoom> findByChatRoomInfo(String chatRoomId){
        return chatRoomRepository.findById(chatRoomId);

    }


    @Transactional
    public void deleteChatRoom(String chatRoomId) {
        chatRoomRepository.deleteById(chatRoomId);
        //chatRepository.deleteByChatRoomId(chatRoomId);
    }


}
