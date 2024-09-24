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


    // 채팅방 생성하기
    @Transactional
    public ChatRoom createChatRoom(Long estimateId, Long userId) {

        Optional<ChatRoom> existingChatRoom = chatRoomRepository.findByEstimateId(estimateId);

        if(existingChatRoom.isPresent()) {
            return existingChatRoom.get();
        }

        else {
            Estimate estimate = estimateRepository.findById(estimateId)
                    .orElseThrow(() -> new IllegalArgumentException("estimate를 찾을 수 없습니다."));
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("client를 찾을 수 없습니다."));

            Long clientId = user.getUserId();
            Long detectiveId = estimate.getDetective().getUser().getUserId();

            List<ChatRoom.Participant> participants = List.of(
                    new ChatRoom.Participant(clientId, "c", false),
                    new ChatRoom.Participant(detectiveId, "d",false)
            );

            // ChatRoom 생성
            ChatRoom chatRoom = ChatRoom.builder()
                    .estimateId(estimateId)
                    .participants(participants)
                    .createdAt(LocalDateTime.now())
                    .build();
            return chatRoomRepository.save(chatRoom);
        }
    }

    // 권한 확인하기
    @Transactional
    public boolean accessChatRoom(String chatRoomId, Long userId) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findById(chatRoomId);
        return chatRoom.map(room -> room.getParticipants().stream()
                .anyMatch(participant -> participant.getUserId().equals(userId))).orElse(false);
    }

    // 개인정보 동의하기
    public void isAcceptedPrivacy(String chatRoomId, Long userId) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findById(chatRoomId);

        if (chatRoom.isPresent()) {
            // 해당 채팅방에서 해당 사용자의 개인정보 동의 상태를 true로 업데이트
            chatRoom.get().getParticipants().stream()
                    .filter(participant -> participant.getUserId().equals(userId))
                    .findFirst()
                    .ifPresent(participant -> participant.setAcceptedPrivacy(true));
            chatRoomRepository.save(chatRoom.get());
        } else {
            throw new IllegalArgumentException("채팅방을 찾을 수 없습니다.");
        }
    }

    // 개인정보 동의 여부 확인하기
    @Transactional
    public boolean checkIsAcceptedPrivacy(String chatRoomId, Long userId) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findById(chatRoomId);

        return chatRoom.map(room -> room.getParticipants().stream()
                .filter(participant -> participant.getUserId().equals(userId))
                .findFirst()
                .map(ChatRoom.Participant::getAcceptedPrivacy)
                .orElse(false)).orElse(false);
    }


    // 채팅방 리스트 불러오기
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
                                .filter(participant -> "d".equals(participant.getRole()))
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
    // userId 관한 채팅방 리스트 모든 데이터 가져오기
    @Transactional
    public List<ChatRoom> getChatRoomList(Long userId){
        return chatRoomRepository.findBychatRoomList(userId);
    }

    // 채팅방의 내역 불러오기
    @Transactional
    public Optional<ChatRoom> findByChatRoomInfo(String chatRoomId){
        return chatRoomRepository.findById(chatRoomId);

    }

    // 채팅방 삭제
    @Transactional
    public void deleteChatRoom(String chatRoomId) {
        chatRoomRepository.deleteById(chatRoomId);
        //chatRepository.deleteByChatRoomId(chatRoomId);
    }


}
