package com.study.ecommerce.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void sendNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    public void sendNotifications(List<Notification> notifications) {
        notificationRepository.saveAll(notifications);
    }
}
