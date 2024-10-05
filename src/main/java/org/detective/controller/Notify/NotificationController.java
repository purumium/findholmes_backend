package org.detective.controller.Notify;

import org.detective.dto.NotificationDTO;
import org.detective.services.Notify.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;
    public static Map<Long,SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // 서버에 클라이언트를 등록하는 메서드
    @GetMapping("/subscribe")
    public SseEmitter subscribe(@RequestParam Long userId) {
        return notificationService.subscribe(userId);
    }

    // 미확인 알림 개수를 불러오는 메서드
    @GetMapping("/receive")
    public int loadNotificationCount(@RequestParam Long userId) {
        return notificationService.loadNotificationCount(userId);
    }

    //받은 알림 리스트를 조회하는 메서드
    @GetMapping("/list")
    public List<NotificationDTO> loadNotifications(@RequestParam Long userId) {
        return notificationService.loadNotifications(userId);
    }

    //안읽은 메시지 개수를 불러오는 메서드
    @GetMapping("chatCount")
    public void receiveChatCount(@RequestParam Long userId) {
        notificationService.notifyChatCount(userId);
    }
}
