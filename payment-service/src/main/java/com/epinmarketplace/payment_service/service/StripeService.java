package com.epinmarketplace.payment_service.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.RefundCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    @Value("${stripe.secret-key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public PaymentIntent createPaymentIntent(BigDecimal amount, String currency, String description) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount.multiply(new BigDecimal("100")).longValue()) // Convert to cents
                .setCurrency(currency.toLowerCase())
                .setDescription(description)
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                .setEnabled(true)
                                .build()
                )
                .build();

        return PaymentIntent.create(params);
    }

    public Map<String, Object> confirmPaymentIntent(String paymentIntentId) throws StripeException {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("id", paymentIntent.getId());
        result.put("status", paymentIntent.getStatus());
        result.put("amount", paymentIntent.getAmount());
        result.put("currency", paymentIntent.getCurrency());
        result.put("description", paymentIntent.getDescription());
        
        return result;
    }

    public Map<String, Object> cancelPaymentIntent(String paymentIntentId) throws StripeException {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        PaymentIntent cancelledIntent = paymentIntent.cancel();
        
        Map<String, Object> result = new HashMap<>();
        result.put("id", cancelledIntent.getId());
        result.put("status", cancelledIntent.getStatus());
        result.put("amount", cancelledIntent.getAmount());
        result.put("currency", cancelledIntent.getCurrency());
        result.put("description", cancelledIntent.getDescription());
        
        return result;
    }

    public Map<String, Object> getPaymentStatus(String paymentIntentId) throws StripeException {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        
        Map<String, Object> status = new HashMap<>();
        status.put("id", paymentIntent.getId());
        status.put("status", paymentIntent.getStatus());
        status.put("amount", paymentIntent.getAmount());
        status.put("currency", paymentIntent.getCurrency());
        status.put("description", paymentIntent.getDescription());
        
        return status;
    }

    public Map<String, Object> createRefund(String paymentIntentId, BigDecimal amount) throws StripeException {
        RefundCreateParams params = RefundCreateParams.builder()
                .setPaymentIntent(paymentIntentId)
                .setAmount(amount.multiply(new BigDecimal("100")).longValue())
                .build();

        Refund refund = Refund.create(params);
        
        Map<String, Object> result = new HashMap<>();
        result.put("id", refund.getId());
        result.put("status", refund.getStatus());
        result.put("amount", refund.getAmount());
        result.put("currency", refund.getCurrency());
        result.put("payment_intent", refund.getPaymentIntent());
        
        return result;
    }
}
