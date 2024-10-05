package org.detective.controller.UserPoint;

import lombok.RequiredArgsConstructor;
import org.detective.repository.ChatRoomRepository;
import org.detective.repository.UserPointRepository;
import org.detective.services.UserPointService;
import org.detective.services.chat.ChatRoomService;
import org.detective.util.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserPointController {
    private final UserPointService userPointService;
    private final ChatRoomService chatRoomService;

    @PostMapping("/chatroom/{chatRoomId}/unlimitedChat")
    public ResponseEntity<String> usePoints(@AuthenticationPrincipal CustomUserDetails user, @RequestParam Long points, @PathVariable String chatRoomId) {
        Long userId = user.getUserId();
        System.err.println("채팅제한 컨트롤러");
        try {
            System.err.println("채팅제한 컨트롤러 try");
            userPointService.usePoints(userId, points, chatRoomId);
            return ResponseEntity.ok(points + "포인트 사용");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
