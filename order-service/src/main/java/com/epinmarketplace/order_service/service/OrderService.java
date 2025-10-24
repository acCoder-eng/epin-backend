package com.epinmarketplace.order_service.service;

import com.epinmarketplace.order_service.client.ProductServiceClient;
import com.epinmarketplace.order_service.dto.OrderDto;
import com.epinmarketplace.order_service.dto.OrderItemDto;
import com.epinmarketplace.order_service.entity.Order;
import com.epinmarketplace.order_service.entity.OrderItem;
import com.epinmarketplace.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductServiceClient productServiceClient;
    
    public OrderDto createOrder(OrderDto orderDto) {
        // Generate unique order number
        String orderNumber = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setUserId(orderDto.getUserId());
        order.setTotalAmount(orderDto.getTotalAmount());
        order.setCurrency(orderDto.getCurrency());
        order.setStatus(orderDto.getStatus());
        order.setPaymentMethod(orderDto.getPaymentMethod());
        order.setPaymentStatus(orderDto.getPaymentStatus());
        order.setShippingAddress(orderDto.getShippingAddress());
        order.setBillingAddress(orderDto.getBillingAddress());
        order.setNotes(orderDto.getNotes());
        
        Order savedOrder = orderRepository.save(order);
        
        // Create order items
        if (orderDto.getOrderItems() != null) {
            for (OrderItemDto itemDto : orderDto.getOrderItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(savedOrder);
                orderItem.setProductId(itemDto.getProductId());
                orderItem.setQuantity(itemDto.getQuantity());
                orderItem.setUnitPrice(itemDto.getUnitPrice());
                orderItem.setTotalPrice(itemDto.getTotalPrice());
                // Save order item (you might need OrderItemRepository)
            }
        }
        
        return convertToDto(savedOrder);
    }
    
    @Transactional(readOnly = true)
    public Page<OrderDto> getOrdersByUserId(Long userId, Pageable pageable) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(this::convertToDto);
    }
    
    @Transactional(readOnly = true)
    public OrderDto getOrderById(Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isEmpty()) {
            throw new RuntimeException("Order not found");
        }
        return convertToDto(orderOpt.get());
    }
    
    @Transactional(readOnly = true)
    public OrderDto getOrderByOrderNumber(String orderNumber) {
        Optional<Order> orderOpt = orderRepository.findByOrderNumber(orderNumber);
        if (orderOpt.isEmpty()) {
            throw new RuntimeException("Order not found");
        }
        return convertToDto(orderOpt.get());
    }
    
    @Transactional(readOnly = true)
    public List<OrderDto> getOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.findByStatus(status).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<OrderDto> getOrdersByPaymentStatus(Order.PaymentStatus paymentStatus) {
        return orderRepository.findByPaymentStatus(paymentStatus).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public OrderDto updateOrderStatus(Long id, Order.OrderStatus status) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isEmpty()) {
            throw new RuntimeException("Order not found");
        }
        
        Order order = orderOpt.get();
        order.setStatus(status);
        Order savedOrder = orderRepository.save(order);
        
        return convertToDto(savedOrder);
    }
    
    public OrderDto updatePaymentStatus(Long id, Order.PaymentStatus paymentStatus) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isEmpty()) {
            throw new RuntimeException("Order not found");
        }
        
        Order order = orderOpt.get();
        order.setPaymentStatus(paymentStatus);
        
        // If payment is successful, update order status to PAID
        if (paymentStatus == Order.PaymentStatus.PAID) {
            order.setStatus(Order.OrderStatus.PAID);
        }
        
        Order savedOrder = orderRepository.save(order);
        
        return convertToDto(savedOrder);
    }
    
    public OrderDto cancelOrder(Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isEmpty()) {
            throw new RuntimeException("Order not found");
        }
        
        Order order = orderOpt.get();
        
        // Only allow cancellation of pending orders
        if (order.getStatus() != Order.OrderStatus.PENDING) {
            throw new RuntimeException("Only pending orders can be cancelled");
        }
        
        order.setStatus(Order.OrderStatus.CANCELLED);
        Order savedOrder = orderRepository.save(order);
        
        return convertToDto(savedOrder);
    }
    
    public OrderDto completeOrder(Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isEmpty()) {
            throw new RuntimeException("Order not found");
        }
        
        Order order = orderOpt.get();
        
        // Only allow completion of paid orders
        if (order.getStatus() != Order.OrderStatus.PAID) {
            throw new RuntimeException("Only paid orders can be completed");
        }
        
        order.setStatus(Order.OrderStatus.COMPLETED);
        Order savedOrder = orderRepository.save(order);
        
        return convertToDto(savedOrder);
    }
    
    @Transactional(readOnly = true)
    public List<OrderDto> getExpiredPendingOrders() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24); // 24 hours ago
        return orderRepository.findExpiredPendingOrders(cutoffTime).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Long getOrderCountByUserId(Long userId) {
        return orderRepository.countByUserId(userId);
    }
    
    @Transactional(readOnly = true)
    public BigDecimal getTotalSpentByUserId(Long userId) {
        BigDecimal total = orderRepository.getTotalSpentByUserId(userId);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    private OrderDto convertToDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setUserId(order.getUserId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setCurrency(order.getCurrency());
        dto.setStatus(order.getStatus());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setPaymentStatus(order.getPaymentStatus());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setBillingAddress(order.getBillingAddress());
        dto.setNotes(order.getNotes());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        
        // Convert order items if present
        if (order.getOrderItems() != null) {
            List<OrderItemDto> itemDtos = order.getOrderItems().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            dto.setOrderItems(itemDtos);
        }
        
        return dto;
    }
    
    private OrderItemDto convertToDto(OrderItem orderItem) {
        OrderItemDto dto = new OrderItemDto();
        dto.setId(orderItem.getId());
        dto.setOrderId(orderItem.getOrder().getId());
        dto.setProductId(orderItem.getProductId());
        dto.setQuantity(orderItem.getQuantity());
        dto.setUnitPrice(orderItem.getUnitPrice());
        dto.setTotalPrice(orderItem.getTotalPrice());
        dto.setCreatedAt(orderItem.getCreatedAt());
        return dto;
    }
}
