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

    public SseEmitter subscribe(Long userId) {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);

        try{
            sseEmitter.send(SseEmitter.event().name("Connect"));
            System.err.println("성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? 성공? ");
        } catch (IOException e){
            e.printStackTrace();
        }

        NotificationController.sseEmitters.put(userId,sseEmitter);

        sseEmitter.onCompletion(() -> {NotificationController.sseEmitters.remove(userId);});
        sseEmitter.onTimeout(() -> NotificationController.sseEmitters.remove(userId));		// sseEmitter 연결에 타임아웃이 발생할 경우
        sseEmitter.onError((e) -> NotificationController.sseEmitters.remove(userId));

        return sseEmitter;
    }

    public void notifyRequest(NotificationDTO notificationDTO) {
        if (NotificationController.sseEmitters.containsKey(notificationDTO.getReceiverId())) {
            SseEmitter sseEmitterReceiver = NotificationController.sseEmitters.get(notificationDTO.getReceiverId());
            try {
                String message = "새로운 의뢰 요청이 도착했습니다.";
                Notification notification = new Notification(notificationDTO.getSenderId(), notificationDTO.getReceiverId(), notificationDTO.getUrl(), message);
                notificationRepository.save(notification);
                sseEmitterReceiver.send(SseEmitter.event().name("addMessage").data("{\"message\": \"" + message + "\"}"));
            } catch (IOException e) {
                NotificationController.sseEmitters.remove(notificationDTO.getReceiverId());
            }
        }
    }

    public void notifyEstimate(NotificationDTO notificationDTO) {
        System.err.println("제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 ");
        if (NotificationController.sseEmitters.containsKey(notificationDTO.getReceiverId())) {
            SseEmitter sseEmitterReceiver = NotificationController.sseEmitters.get(notificationDTO.getReceiverId());
            try {
                System.err.println("@@@@@@@@@@@@@@@제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 제발 보내져라 ");

                String message = "의뢰글 '"+notificationDTO.getTitle()+"'에 대한 "+notificationDTO.getSenderName()+"님의 답변서가 도착했습니다.";

                Notification notification = new Notification(notificationDTO.getSenderId(), notificationDTO.getReceiverId(), notificationDTO.getUrl(), message);
                notificationRepository.save(notification);
                sseEmitterReceiver.send(SseEmitter.event().name("addMessage").data("{\"message\": \"" + message + "\"}"));
            } catch (IOException e) {
                NotificationController.sseEmitters.remove(notificationDTO.getReceiverId());
            }
        }
    }

    public int loadNotificationCount(Long userId) {
        return notificationRepository.countByReceiverIdAndIsCheck(userId,false);
    }

    public List<NotificationDTO> loadNotifications(Long userId) {
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        List<Notification> notifications = notificationRepository.findByReceiverId(userId);
        for (Notification notification : notifications) {
            notificationDTOS.add(new NotificationDTO(notification.getDescription(),notification.getUrl()));
            notification.setIsCheck(true);
            notificationRepository.save(notification);
        }
        return notificationDTOS;
    }
}
