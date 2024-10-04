package org.detective.services.chat;

import lombok.RequiredArgsConstructor;
import org.detective.dto.ChatRoomDTO;
import org.detective.dto.ChatRoomDetailDTO;
import org.detective.dto.ParticipantDTO;
import org.detective.entity.*;
import org.detective.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final EstimateRepository estimateRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final ChatNotificationRepository chatNotificationRepository;


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

    // 채팅 제한 유무
    @Transactional
    public boolean canSendMessage(String chatRoomId) {
        Optional<ChatRoom> chatRoomOpt = chatRoomRepository.findById(chatRoomId);

        if (chatRoomOpt.isPresent()) {
            ChatRoom chatRoom = chatRoomOpt.get();

            if (chatRoom.isUnlimitedAccess()) {
                return true;
            }

            if (chatRoom.getChatCount() < 5 ) {
                return true;
            } else {
                return false;
            }
        }
        throw new RuntimeException("채팅방을 찾을 수 없습니다.");

    }

    @Transactional
    public void increaseChatCount(String chatRoomId) {
        if (canSendMessage(chatRoomId)) {
            ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                    .orElseThrow();
            chatRoom.increaseChatCount();
            chatRoomRepository.save(chatRoom);
        } else {
            throw new IllegalStateException("채팅 제한에 도달했습니다. 포인트를 사용해 채팅을 무제한으로 할 수 있습니다.");
        }
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
                                    return new ParticipantDTO(detective.getUserId(),detective.getUserName(), participant.getRole());
                                })
                                .collect(Collectors.toList());
                        Optional<Chat> lastChat = chatRepository.findFirstByChatRoomIdOrderBySendTimeDesc(chatRoom.getId());
                        String lastMessage = lastChat.map(Chat::getMessage).orElse("");
                        LocalDateTime lastSendTime = lastChat.map(Chat::getSendTime).orElse(chatRoom.getCreatedAt());

                        // ChatNotification 조회
                        Optional<ChatNotification> notificationOpt = chatNotificationRepository.findByUserIdAndChatRoomId(userId, chatRoom.getId());
                        int notificationCount = notificationOpt.map(ChatNotification::getNotification).orElse(0);  // 알림 값 추가

                        return new ChatRoomDTO(chatRoom.getId(), participants, lastMessage, lastSendTime, notificationCount);
                    })
                    .sorted(Comparator.comparing(ChatRoomDTO::getLastChatTime))
                    .collect(Collectors.toList());

            // ROLE_DETECTIVE이면 role이 "c"인 참여자만 반환
        } else if (role.equals("ROLE_DETECTIVE")) {
            chatRooms = chatRoomRepository.findByUserIdAndRoleIsDetective(userId); // 이미 "d"로 필터된 결과 가져오기
            return chatRooms.stream()
                    .map(chatRoom -> {
                        // participants에서 userId로 username 조회
                        List<ParticipantDTO> participants = chatRoom.getParticipants().stream()
                                .filter(participant -> "c".equals(participant.getRole()))
                                .map(participant -> {
                                    User client = userRepository.findById(participant.getUserId())
                                            .orElseThrow(() -> new IllegalArgumentException("상대 의뢰인을 찾을 수 없습니다."));
                                    return new ParticipantDTO(client.getUserId(), client.getUserName(), participant.getRole());
                                })
                                .collect(Collectors.toList());

                        Optional<Chat> lastChat = chatRepository.findFirstByChatRoomIdOrderBySendTimeDesc(chatRoom.getId());
                        String lastMessage = lastChat.map(Chat::getMessage).orElse("");
                        LocalDateTime lastSendTime = lastChat.map(Chat::getSendTime).orElse(chatRoom.getCreatedAt());

                        // ChatNotification 조회
                        Optional<ChatNotification> notificationOpt = chatNotificationRepository.findByUserIdAndChatRoomId(userId, chatRoom.getId());
                        int notificationCount = notificationOpt.map(ChatNotification::getNotification).orElse(0);  // 알림 값 추가

                        return new ChatRoomDTO(chatRoom.getId(), participants, lastMessage, lastSendTime, notificationCount);
                    })
                    .sorted(Comparator.comparing(ChatRoomDTO::getLastChatTime))
                    .collect(Collectors.toList());

        } else {
            throw new IllegalArgumentException("잘못된 역할입니다.");
        }
    }
    // userId 관한 채팅방 리스트 모든 데이터 가져오기 (사용안함)
    @Transactional
    public List<ChatRoom> getChatRoomList(Long userId){
        return chatRoomRepository.findBychatRoomList(userId);
    }

    // 채팅방 관련 정보 불러오기
    @Transactional
    public ChatRoomDetailDTO findByChatRoomInfo(String chatRoomId){
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("채팅방 찾기 실패하였습니다."));
        Long estimateId = chatRoom.getEstimateId();
        Estimate estimate = estimateRepository.findById(estimateId)
                .orElseThrow(() -> new RuntimeException("견적서 찾기 실패하였습니다."));
        String title = estimate.getTitle();
        int price = estimate.getPrice();
        Request request = estimate.getRequest();
        Speciality requestSpeciality = request.getSpeciality();
        List<ChatRoom.Participant> participants = chatRoom.getParticipants();
        List<Long> userIds = participants.stream()
                .map(ChatRoom.Participant::getUserId) // Participant에서 userId 추출
                .collect(Collectors.toList());
        List<User> users = userRepository.findAllById(userIds);
        List<ParticipantDTO> participantDTOs = users.stream()
                .map(user -> {
                    // Participant의 userId에 맞는 username을 DTO로 변환
                    ChatRoom.Participant participant = participants.stream()
                            .filter(p -> Objects.equals(p.getUserId(), user.getUserId()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Participant not found for userId: " + user.getUserId()));
                    return new ParticipantDTO(participant.getUserId(), user.getUserName(), participant.getRole());
                })
                .collect(Collectors.toList());
        return new ChatRoomDetailDTO(chatRoom.getId(), participantDTOs, title, price, requestSpeciality);
    }

    // 채팅방 유무에 따른 리뷰 작성
    @Transactional
    public boolean getChatRoomExisting(Long estimateId) {
        Optional<ChatRoom> existingChatRoom = chatRoomRepository.findByEstimateId(estimateId);
        return existingChatRoom.isPresent();
    }

    // 채팅방 삭제
    @Transactional
    public void deleteChatRoom(String chatRoomId) {
        chatRoomRepository.deleteById(chatRoomId);
        //chatRepository.deleteByChatRoomId(chatRoomId);
    }


}
