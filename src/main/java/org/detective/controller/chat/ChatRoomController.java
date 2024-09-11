package org.detective.controller.chat;

import lombok.RequiredArgsConstructor;
import org.detective.dto.ChatRoomDTO;
import org.detective.entity.ChatRoom;
import org.detective.entity.User;
import org.detective.repository.EstimateRepository;
import org.detective.services.chat.ChatRoomService;
import org.detective.util.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final EstimateRepository estimateRepository;

    @PostMapping("/create")
    public ResponseEntity<ChatRoom> createChatRoom(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam Long estimateId) {
        Long userId = userDetails.getUserId();
        ChatRoom chatRoom = chatRoomService.createChatRoom(estimateId, userId);
        return ResponseEntity.ok(chatRoom);
    }

    @GetMapping("/chatList")
    public ResponseEntity<List<ChatRoomDTO>> getChatRoomsByUserId(@AuthenticationPrincipal CustomUserDetails userDetails){
        Long userId = userDetails.getUserId();
        List<ChatRoomDTO> chatRooms = chatRoomService.findByChatList(userId);
        return ResponseEntity.ok(chatRooms);
    }

    @DeleteMapping("/delete/{chatRoomId}")
    public ResponseEntity<ChatRoom> deleteChatRoom(@PathVariable String chatRoomId){
        chatRoomService.deleteChatRoom(chatRoomId);
        return ResponseEntity.noContent().build();
    }


}
