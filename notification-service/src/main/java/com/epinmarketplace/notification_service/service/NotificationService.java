package com.epinmarketplace.notification_service.service;

import com.epinmarketplace.notification_service.dto.NotificationDto;
import com.epinmarketplace.notification_service.dto.NotificationMessage;
import com.epinmarketplace.notification_service.entity.Notification;
import com.epinmarketplace.notification_service.repository.NotificationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationService {
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    public NotificationDto createNotification(NotificationDto notificationDto) {
        Notification notification = new Notification();
        notification.setUserId(notificationDto.getUserId());
        notification.setTitle(notificationDto.getTitle());
        notification.setMessage(notificationDto.getMessage());
        notification.setType(notificationDto.getType());
        notification.setIsRead(notificationDto.getIsRead());
        notification.setData(notificationDto.getData());
        
        Notification savedNotification = notificationRepository.save(notification);
        return convertToDto(savedNotification);
    }
    
    public void processNotificationMessage(NotificationMessage message) {
        Notification notification = new Notification();
        notification.setUserId(message.getUserId());
        notification.setTitle(message.getTitle());
        notification.setMessage(message.getMessage());
        notification.setType(message.getType());
        notification.setIsRead(false);
        
        // Convert data map to JSON string
        if (message.getData() != null) {
            try {
                notification.setData(objectMapper.writeValueAsString(message.getData()));
            } catch (Exception e) {
                notification.setData("{}");
            }
        }
        
        notificationRepository.save(notification);
    }
    
    @Transactional(readOnly = true)
    public Page<NotificationDto> getNotificationsByUserId(Long userId, Pageable pageable) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(this::convertToDto);
    }
    
    @Transactional(readOnly = true)
    public List<NotificationDto> getUnreadNotificationsByUserId(Long userId) {
        return notificationRepository.findUnreadByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<NotificationDto> getNotificationsByUserIdAndType(Long userId, String type) {
        return notificationRepository.findByUserIdAndTypeOrderByCreatedAtDesc(userId, type).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public NotificationDto getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
    }
    
    public NotificationDto markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        
        notification.setIsRead(true);
        Notification savedNotification = notificationRepository.save(notification);
        return convertToDto(savedNotification);
    }
    
    public void markAllAsRead(Long userId) {
        List<Notification> unreadNotifications = notificationRepository.findUnreadByUserId(userId);
        for (Notification notification : unreadNotifications) {
            notification.setIsRead(true);
        }
        notificationRepository.saveAll(unreadNotifications);
    }
    
    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new RuntimeException("Notification not found");
        }
        notificationRepository.deleteById(id);
    }
    
    public void deleteReadNotifications(Long userId) {
        notificationRepository.deleteByUserIdAndIsReadTrue(userId);
    }
    
    @Transactional(readOnly = true)
    public Long getUnreadCount(Long userId) {
        return notificationRepository.countUnreadByUserId(userId);
    }
    
    @Transactional(readOnly = true)
    public List<NotificationDto> getNotificationsByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return notificationRepository.findByUserIdAndDateRange(userId, startDate, endDate).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public void cleanupOldNotifications(int daysOld) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysOld);
        List<Notification> oldNotifications = notificationRepository.findOldNotifications(cutoffDate);
        notificationRepository.deleteAll(oldNotifications);
    }
    
    private NotificationDto convertToDto(Notification notification) {
        NotificationDto dto = new NotificationDto();
        dto.setId(notification.getId());
        dto.setUserId(notification.getUserId());
        dto.setTitle(notification.getTitle());
        dto.setMessage(notification.getMessage());
        dto.setType(notification.getType());
        dto.setIsRead(notification.getIsRead());
        dto.setData(notification.getData());
        dto.setCreatedAt(notification.getCreatedAt());
        return dto;
    }
}
