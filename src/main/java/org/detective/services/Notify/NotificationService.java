package org.detective.services.Notify;

import org.detective.controller.Notify.NotificationController;
import org.detective.dto.NotificationDTO;
import org.detective.entity.Notification;
import org.detective.repository.DetectiveRepository;
import org.detective.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    private final DetectiveRepository detectiveRepository;
    private final NotificationRepository notificationRepository;

    public NotificationService(DetectiveRepository detectiveRepository, NotificationRepository notificationRepository) {
        this.detectiveRepository = detectiveRepository;
        this.notificationRepository = notificationRepository;
    }

    //서버에 클라이언트를 등록시키는 메서드
    public SseEmitter subscribe(Long userId) {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);

        try{
            sseEmitter.send(SseEmitter.event().name("Connect"));
        } catch (IOException e){
            e.printStackTrace();
        }

        NotificationController.sseEmitters.put(userId,sseEmitter);

        sseEmitter.onCompletion(() -> {NotificationController.sseEmitters.remove(userId);});
        sseEmitter.onTimeout(() -> NotificationController.sseEmitters.remove(userId));		// sseEmitter 연결에 타임아웃이 발생할 경우
        sseEmitter.onError((e) -> NotificationController.sseEmitters.remove(userId));

        return sseEmitter;
    }

    //고객이 의뢰글을 작성하면 해당하는 탐정에게 알림을 전달하는 메서드
    public void notifyRequest(NotificationDTO notificationDTO) {
        String message = "새로운 의뢰 '"+notificationDTO.getTitle()+"'가 도착했습니다.";

        if (NotificationController.sseEmitters.containsKey(notificationDTO.getReceiverId())) {
            SseEmitter sseEmitterReceiver = NotificationController.sseEmitters.get(notificationDTO.getReceiverId());
            try {
                System.err.println(message);
                sseEmitterReceiver.send(SseEmitter.event().name("addMessage").data("{\"message\": \"" + message + "\"}"));
            } catch (IOException e) {
                NotificationController.sseEmitters.remove(notificationDTO.getReceiverId());
            }
        }

        Notification notification = new Notification(notificationDTO.getSenderId(), notificationDTO.getReceiverId(), notificationDTO.getUrl(), message);
        notificationRepository.save(notification);
    }

    //탐정이 답변서를 작성하면 고객에게 알림을 전달하는 메서드
    public void notifyEstimate(NotificationDTO notificationDTO) {
        String message = "의뢰글 '"+notificationDTO.getTitle()+"'에 대한 "+notificationDTO.getSenderName()+"님의 답변서가 도착했습니다.";

        if (NotificationController.sseEmitters.containsKey(notificationDTO.getReceiverId())) {
            SseEmitter sseEmitterReceiver = NotificationController.sseEmitters.get(notificationDTO.getReceiverId());
            try {
                System.err.println(message);
                sseEmitterReceiver.send(SseEmitter.event().name("addMessage").data("{\"message\": \"" + message + "\"}"));
            } catch (IOException e) {
                NotificationController.sseEmitters.remove(notificationDTO.getReceiverId());
            }
        }
        Notification notification = new Notification(notificationDTO.getSenderId(), notificationDTO.getReceiverId(), notificationDTO.getUrl(), message);
        notificationRepository.save(notification);
    }

    //미확인 알림개수를 불러오는 메서드
    public int loadNotificationCount(Long userId) {
        return notificationRepository.countByReceiverIdAndIsCheck(userId,false);
    }

    //받은 알림 리스트를 조회하는 메서드
    public List<NotificationDTO> loadNotifications(Long userId) {
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        List<Notification> notifications = notificationRepository.findByReceiverId(userId);
        for (Notification notification : notifications) {
            notificationDTOS.add(new NotificationDTO(notification.getDescription(),notification.getUrl(),notification.getNotificationTime()));
            notification.setIsCheck(true);
            notificationRepository.save(notification);
        }
        return notificationDTOS;
    }

    //메시지 수신 시 알림을 전달하는 메서드
    public void notifyChatCount(Long userId) {
        if (NotificationController.sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitterReceiver = NotificationController.sseEmitters.get(userId);
            try {
                System.err.println("채팅 SSE 실행 "+"채팅 SSE 실행"+"채팅 SSE 실행 "+"채팅 SSE 실행"+"채팅 SSE 실행 "+"채팅 SSE 실행"+"채팅 SSE 실행 "+"채팅 SSE 실행"+"채팅 SSE 실행 "+"채팅 SSE 실행"+"채팅 SSE 실행 "+"채팅 SSE 실행"+"채팅 SSE 실행 "+"채팅 SSE 실행"+"채팅 SSE 실행 "+"채팅 SSE 실행"+"채팅 SSE 실행 "+"채팅 SSE 실행"+"채팅 SSE 실행 "+"채팅 SSE 실행"+"채팅 SSE 실행 "+"채팅 SSE 실행"+"채팅 SSE 실행 "+"채팅 SSE 실행"+"채팅 SSE 실행 "+"채팅 SSE 실행"+"채팅 SSE 실행 "+"채팅 SSE 실행");
                sseEmitterReceiver.send(SseEmitter.event().name("ReceiveChat").data("새로운 채팅메시지"));
            } catch (IOException e) {
                NotificationController.sseEmitters.remove(userId);
            }
        }
    }
}
