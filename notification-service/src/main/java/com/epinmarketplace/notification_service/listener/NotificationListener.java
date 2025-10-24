package com.epinmarketplace.notification_service.listener;

import com.epinmarketplace.notification_service.config.RabbitMQConfig;
import com.epinmarketplace.notification_service.dto.NotificationMessage;
import com.epinmarketplace.notification_service.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
    
    @Autowired
    private NotificationService notificationService;
    
    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void handleOrderNotification(NotificationMessage message) {
        System.out.println("Received order notification: " + message);
        notificationService.processNotificationMessage(message);
    }
    
    @RabbitListener(queues = RabbitMQConfig.PAYMENT_QUEUE)
    public void handlePaymentNotification(NotificationMessage message) {
        System.out.println("Received payment notification: " + message);
        notificationService.processNotificationMessage(message);
    }
    
    @RabbitListener(queues = RabbitMQConfig.PRODUCT_QUEUE)
    public void handleProductNotification(NotificationMessage message) {
        System.out.println("Received product notification: " + message);
        notificationService.processNotificationMessage(message);
    }
    
    @RabbitListener(queues = RabbitMQConfig.USER_QUEUE)
    public void handleUserNotification(NotificationMessage message) {
        System.out.println("Received user notification: " + message);
        notificationService.processNotificationMessage(message);
    }
}
