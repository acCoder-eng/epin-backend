package com.epinmarketplace.payment_service.service;

import com.epinmarketplace.payment_service.dto.PaymentDto;
import com.epinmarketplace.payment_service.dto.PaymentRequestDto;
import com.epinmarketplace.payment_service.entity.Payment;
import com.epinmarketplace.payment_service.entity.Payment.PaymentStatus;
import com.epinmarketplace.payment_service.repository.PaymentRepository;
import com.epinmarketplace.payment_service.service.StripeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private StripeService stripeService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    public PaymentDto createPayment(PaymentRequestDto paymentRequest) {
        Payment payment = new Payment();
        payment.setOrderId(paymentRequest.getOrderId());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setPaymentProvider(paymentRequest.getPaymentProvider());
        payment.setAmount(paymentRequest.getAmount());
        payment.setCurrency(paymentRequest.getCurrency());
        payment.setStatus(PaymentStatus.PENDING);
        
        // Store payment data as JSON
        try {
            Map<String, Object> paymentData = new HashMap<>();
            paymentData.put("stripeToken", paymentRequest.getStripeToken());
            paymentData.put("stripePaymentMethodId", paymentRequest.getStripePaymentMethodId());
            payment.setPaymentData(objectMapper.writeValueAsString(paymentData));
        } catch (Exception e) {
            // Handle JSON serialization error
            payment.setPaymentData("{}");
        }
        
        Payment savedPayment = paymentRepository.save(payment);
        return convertToDto(savedPayment);
    }
    
    public PaymentDto processPayment(Long paymentId) {
        Optional<Payment> paymentOpt = paymentRepository.findById(paymentId);
        if (paymentOpt.isEmpty()) {
            throw new RuntimeException("Payment not found");
        }
        
        Payment payment = paymentOpt.get();
        
        try {
            // Create Stripe Payment Intent
            com.stripe.model.PaymentIntent stripePaymentIntent = stripeService.createPaymentIntent(
                    payment.getAmount(),
                    payment.getCurrency(),
                    "Payment for Order #" + payment.getOrderId()
            );
            
            // Update payment with Stripe transaction ID
            payment.setTransactionId(stripePaymentIntent.getId());
            payment.setStatus(PaymentStatus.PENDING);
            
            Payment savedPayment = paymentRepository.save(payment);
            return convertToDto(savedPayment);
            
        } catch (Exception e) {
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
            throw new RuntimeException("Payment processing failed: " + e.getMessage());
        }
    }
    
    public PaymentDto confirmPayment(Long paymentId) {
        Optional<Payment> paymentOpt = paymentRepository.findById(paymentId);
        if (paymentOpt.isEmpty()) {
            throw new RuntimeException("Payment not found");
        }
        
        Payment payment = paymentOpt.get();
        
        if (payment.getTransactionId() == null) {
            throw new RuntimeException("No transaction ID found for payment");
        }
        
        try {
            // Confirm Stripe Payment Intent
            Map<String, Object> stripeResponse = stripeService.confirmPaymentIntent(payment.getTransactionId());
            
            String status = (String) stripeResponse.get("status");
            if ("succeeded".equals(status)) {
                payment.setStatus(PaymentStatus.SUCCESS);
            } else {
                payment.setStatus(PaymentStatus.FAILED);
            }
            
            Payment savedPayment = paymentRepository.save(payment);
            return convertToDto(savedPayment);
            
        } catch (Exception e) {
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
            throw new RuntimeException("Payment confirmation failed: " + e.getMessage());
        }
    }
    
    public PaymentDto cancelPayment(Long paymentId) {
        Optional<Payment> paymentOpt = paymentRepository.findById(paymentId);
        if (paymentOpt.isEmpty()) {
            throw new RuntimeException("Payment not found");
        }
        
        Payment payment = paymentOpt.get();
        
        if (payment.getTransactionId() != null) {
            try {
                // Cancel Stripe Payment Intent
                stripeService.cancelPaymentIntent(payment.getTransactionId());
            } catch (Exception e) {
                // Log error but continue with cancellation
                System.err.println("Error cancelling Stripe payment: " + e.getMessage());
            }
        }
        
        payment.setStatus(PaymentStatus.CANCELLED);
        Payment savedPayment = paymentRepository.save(payment);
        return convertToDto(savedPayment);
    }
    
    public PaymentDto refundPayment(Long paymentId, BigDecimal refundAmount) {
        Optional<Payment> paymentOpt = paymentRepository.findById(paymentId);
        if (paymentOpt.isEmpty()) {
            throw new RuntimeException("Payment not found");
        }
        
        Payment payment = paymentOpt.get();
        
        if (payment.getStatus() != PaymentStatus.SUCCESS) {
            throw new RuntimeException("Only successful payments can be refunded");
        }
        
        if (payment.getTransactionId() == null) {
            throw new RuntimeException("No transaction ID found for payment");
        }
        
        try {
            // Create Stripe Refund
            Map<String, Object> stripeResponse = stripeService.createRefund(
                    payment.getTransactionId(),
                    refundAmount
            );
            
            payment.setStatus(PaymentStatus.REFUNDED);
            Payment savedPayment = paymentRepository.save(payment);
            return convertToDto(savedPayment);
            
        } catch (Exception e) {
            throw new RuntimeException("Refund failed: " + e.getMessage());
        }
    }
    
    @Transactional(readOnly = true)
    public PaymentDto getPaymentById(Long id) {
        Optional<Payment> paymentOpt = paymentRepository.findById(id);
        if (paymentOpt.isEmpty()) {
            throw new RuntimeException("Payment not found");
        }
        return convertToDto(paymentOpt.get());
    }
    
    @Transactional(readOnly = true)
    public List<PaymentDto> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findByOrderIdOrderByCreatedAtDesc(orderId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Page<PaymentDto> getPaymentsByOrderId(Long orderId, Pageable pageable) {
        return paymentRepository.findByOrderId(orderId, pageable)
                .map(this::convertToDto);
    }
    
    @Transactional(readOnly = true)
    public List<PaymentDto> getPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.findByStatus(status).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Optional<PaymentDto> getPaymentByTransactionId(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId)
                .map(this::convertToDto);
    }
    
    @Transactional(readOnly = true)
    public List<PaymentDto> getExpiredPendingPayments() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24); // 24 hours ago
        return paymentRepository.findExpiredPendingPayments(cutoffTime).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Long getSuccessfulPaymentCount() {
        return paymentRepository.countSuccessfulPayments();
    }
    
    @Transactional(readOnly = true)
    public BigDecimal getTotalSuccessfulAmount() {
        BigDecimal total = paymentRepository.getTotalSuccessfulAmount();
        return total != null ? total : BigDecimal.ZERO;
    }
    
    private PaymentDto convertToDto(Payment payment) {
        PaymentDto dto = new PaymentDto();
        dto.setId(payment.getId());
        dto.setOrderId(payment.getOrderId());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setPaymentProvider(payment.getPaymentProvider());
        dto.setTransactionId(payment.getTransactionId());
        dto.setAmount(payment.getAmount());
        dto.setCurrency(payment.getCurrency());
        dto.setStatus(payment.getStatus());
        dto.setPaymentData(payment.getPaymentData());
        dto.setCreatedAt(payment.getCreatedAt());
        dto.setUpdatedAt(payment.getUpdatedAt());
        return dto;
    }
}
