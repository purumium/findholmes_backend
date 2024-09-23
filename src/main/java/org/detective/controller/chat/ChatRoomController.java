package org.detective.controller.chat;

import lombok.RequiredArgsConstructor;
import org.detective.dto.ChatRoomDTO;
import org.detective.entity.ChatRoom;
import org.detective.repository.ChatRoomRepository;
import org.detective.repository.EstimateRepository;
import org.detective.services.chat.ChatRoomService;
import org.detective.util.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final EstimateRepository estimateRepository;
    private final ChatRoomRepository chatRoomRepository;

    @PostMapping("/create")
    public ResponseEntity<ChatRoom> createChatRoom(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam Long estimateId) {
        Long userId = userDetails.getUserId();
        ChatRoom chatRoom = chatRoomService.createChatRoom(estimateId, userId);
        return ResponseEntity.ok(chatRoom);
    }

    // 개인정보 동의 여부 확인
    @GetMapping("/{chatRoomId}/check-isaccepted")
    public ResponseEntity<Boolean> checkConsent(
            @PathVariable String chatRoomId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUserId();
        boolean consentGiven = chatRoomService.checkIsAcceptedPrivacy(chatRoomId, userId);

        return ResponseEntity.ok(consentGiven);
    }

    // 개인정보 동의 처리
    @PostMapping("/{chatRoomId}/is-accepted")
    public ResponseEntity<String> giveConsent(
            @PathVariable String chatRoomId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUserId();
        chatRoomService.isAcceptedPrivacy(chatRoomId, userId);

        return ResponseEntity.ok("개인정보 동의가 완료되었습니다.");
    }

    @GetMapping("/chatList")
    public ResponseEntity<List<ChatRoomDTO>> getChatRoomsByUserId(@AuthenticationPrincipal CustomUserDetails userDetails){
        Long userId = userDetails.getUserId();
        List<ChatRoomDTO> chatRooms = chatRoomService.findByChatList(userId);
        return ResponseEntity.ok(chatRooms);
    }

//    @GetMapping("/chatList")
//    public ResponseEntity<List<ChatRoom>> getChatRoomsByUserId(@AuthenticationPrincipal CustomUserDetails userDetails){
//        Long userId = userDetails.getUserId();
//        List<ChatRoom> chatRooms = chatRoomService.getChatRoomList(userId);
//        return ResponseEntity.ok(chatRooms);
//    }

    @DeleteMapping("/delete/{chatRoomId}")
    public ResponseEntity<ChatRoom> deleteChatRoom(@PathVariable String chatRoomId){
        chatRoomService.deleteChatRoom(chatRoomId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{chatRoomId}/check-access")
    public ResponseEntity<String> checkAccess(@PathVariable String chatRoomId, @AuthenticationPrincipal CustomUserDetails userDetails){
        Long userId = userDetails.getUserId();
        boolean canAccess = chatRoomService.accessChatRoom(chatRoomId, userId);

        if(canAccess){
            return ResponseEntity.ok("ok");
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근 권한이 없습니다.");
        }

    }

    @GetMapping("/detail/{chatRoomId}")
    public ResponseEntity<ChatRoom> getChatRoom(@PathVariable String chatRoomId){
        Optional<ChatRoom> chatRoomOptional = chatRoomService.findByChatRoomInfo(chatRoomId);
        // Optional 값 확인 후 적절한 응답 반환
        return chatRoomOptional
                .map(chatRoom -> ResponseEntity.ok(chatRoom))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


//    @GetMapping("/check/{estimateId}")
//    public List<ChatRoom> getEstimates(@PathVariable Long estimateId) {
//        return chatRoomRepository.findByEstimateId(estimateId);
//    }


}
