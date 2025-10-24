package com.epinmarketplace.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class GatewayConfig {
    
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:3001"));
        corsConfig.setMaxAge(3600L);
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        corsConfig.setAllowedHeaders(Arrays.asList("*"));
        corsConfig.setAllowCredentials(true);
        corsConfig.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // User Service Routes
                .route("user-service", r -> r.path("/api/users", "/api/users/**")
                        .uri("lb://user-service"))
                
                // Product Service Routes
                .route("product-service", r -> r.path("/api/products", "/api/products/**")
                        .uri("lb://product-service"))
                
                // Category Service Routes
                .route("category-service", r -> r.path("/api/categories", "/api/categories/**")
                        .uri("lb://category-service"))
                
                // Order Service Routes
                .route("order-service", r -> r.path("/api/orders", "/api/orders/**")
                        .uri("lb://order-service"))
                
                // Cart Service Routes
                .route("cart-service", r -> r.path("/api/cart/**")
                        .uri("lb://order-service"))
                
                // Wishlist Service Routes
                .route("wishlist-service", r -> r.path("/api/wishlist/**")
                        .uri("lb://order-service"))
                
                // Payment Service Routes
                .route("payment-service", r -> r.path("/api/payments/**")
                        .uri("lb://payment-service"))
                
                // Notification Service Routes
                .route("notification-service", r -> r.path("/api/notifications/**")
                        .uri("lb://notification-service"))
                
                .build();
    }
}