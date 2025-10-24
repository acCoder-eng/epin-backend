package com.epinmarketplace.payment_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class PaymentRequestDto {
    
    private Long orderId;
    
    @NotBlank(message = "Payment method is required")
    @Size(max = 50, message = "Payment method must not exceed 50 characters")
    private String paymentMethod;
    
    @Size(max = 50, message = "Payment provider must not exceed 50 characters")
    private String paymentProvider;
    
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;
    
    @Size(max = 3, message = "Currency must not exceed 3 characters")
    private String currency = "USD";
    
    private String paymentData;
    
    // Stripe specific fields
    private String stripeToken;
    private String stripePaymentMethodId;
    
    // Constructors
    public PaymentRequestDto() {}
    
    public PaymentRequestDto(Long orderId, String paymentMethod, BigDecimal amount, String currency) {
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.currency = currency;
    }
    
    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public String getPaymentProvider() {
        return paymentProvider;
    }
    
    public void setPaymentProvider(String paymentProvider) {
        this.paymentProvider = paymentProvider;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public String getPaymentData() {
        return paymentData;
    }
    
    public void setPaymentData(String paymentData) {
        this.paymentData = paymentData;
    }
    
    public String getStripeToken() {
        return stripeToken;
    }
    
    public void setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
    }
    
    public String getStripePaymentMethodId() {
        return stripePaymentMethodId;
    }
    
    public void setStripePaymentMethodId(String stripePaymentMethodId) {
        this.stripePaymentMethodId = stripePaymentMethodId;
    }
}
