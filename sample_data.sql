-- Sample Data for E-Pin Marketplace
-- This file contains sample data to populate the database for testing

-- =============================================
-- SAMPLE USERS
-- =============================================

-- Admin User
INSERT INTO users (username, email, password_hash, first_name, last_name, phone, is_active, is_verified, role) VALUES
('admin', 'admin@epin.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Admin', 'User', '+1234567890', true, true, 'ADMIN');

-- Regular Users
INSERT INTO users (username, email, password_hash, first_name, last_name, phone, is_active, is_verified, role) VALUES
('john_doe', 'john@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'John', 'Doe', '+1234567891', true, true, 'USER'),
('jane_smith', 'jane@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Jane', 'Smith', '+1234567892', true, true, 'SELLER');

-- =============================================
-- SAMPLE PRODUCTS
-- =============================================

-- Steam Products
INSERT INTO products (name, description, category_id, seller_id, price, original_price, stock_quantity, min_quantity, max_quantity, image_url, pin_format, pin_type, region, platform, is_active, is_featured) VALUES
('Steam Wallet Code - $10', 'Add $10 to your Steam wallet instantly', 6, 2, 10.00, 10.00, 100, 1, 10, 'steam-10.jpg', 'XXXX-XXXX-XXXX', 'DIGITAL', 'GLOBAL', 'Steam', true, true),
('Steam Wallet Code - $25', 'Add $25 to your Steam wallet instantly', 6, 2, 25.00, 25.00, 50, 1, 5, 'steam-25.jpg', 'XXXX-XXXX-XXXX', 'DIGITAL', 'GLOBAL', 'Steam', true, true),
('Steam Wallet Code - $50', 'Add $50 to your Steam wallet instantly', 6, 2, 50.00, 50.00, 25, 1, 3, 'steam-50.jpg', 'XXXX-XXXX-XXXX', 'DIGITAL', 'GLOBAL', 'Steam', true, false);

-- PlayStation Products
INSERT INTO products (name, description, category_id, seller_id, price, original_price, stock_quantity, min_quantity, max_quantity, image_url, pin_format, pin_type, region, platform, is_active, is_featured) VALUES
('PlayStation Store Gift Card - $10', 'PlayStation Store digital gift card', 7, 2, 10.00, 10.00, 75, 1, 10, 'psn-10.jpg', 'XXXX-XXXX-XXXX', 'DIGITAL', 'US', 'PlayStation', true, true),
('PlayStation Store Gift Card - $25', 'PlayStation Store digital gift card', 7, 2, 25.00, 25.00, 40, 1, 5, 'psn-25.jpg', 'XXXX-XXXX-XXXX', 'DIGITAL', 'US', 'PlayStation', true, true),
('PlayStation Plus 1 Month', 'PlayStation Plus subscription for 1 month', 7, 2, 9.99, 9.99, 30, 1, 12, 'ps-plus-1m.jpg', 'XXXX-XXXX-XXXX', 'DIGITAL', 'US', 'PlayStation', true, false);

-- Xbox Products
INSERT INTO products (name, description, category_id, seller_id, price, original_price, stock_quantity, min_quantity, max_quantity, image_url, pin_format, pin_type, region, platform, is_active, is_featured) VALUES
('Xbox Live Gift Card - $10', 'Xbox Live digital gift card', 8, 2, 10.00, 10.00, 60, 1, 10, 'xbox-10.jpg', 'XXXX-XXXX-XXXX', 'DIGITAL', 'US', 'Xbox', true, true),
('Xbox Game Pass Ultimate 1 Month', 'Xbox Game Pass Ultimate subscription', 8, 2, 14.99, 14.99, 20, 1, 12, 'xbox-gpu-1m.jpg', 'XXXX-XXXX-XXXX', 'DIGITAL', 'US', 'Xbox', true, true);

-- Nintendo Products
INSERT INTO products (name, description, category_id, seller_id, price, original_price, stock_quantity, min_quantity, max_quantity, image_url, pin_format, pin_type, region, platform, is_active, is_featured) VALUES
('Nintendo eShop Gift Card - $10', 'Nintendo eShop digital gift card', 9, 2, 10.00, 10.00, 45, 1, 10, 'nintendo-10.jpg', 'XXXX-XXXX-XXXX', 'DIGITAL', 'US', 'Nintendo', true, false),
('Nintendo Switch Online 1 Month', 'Nintendo Switch Online subscription', 9, 2, 3.99, 3.99, 35, 1, 12, 'nso-1m.jpg', 'XXXX-XXXX-XXXX', 'DIGITAL', 'US', 'Nintendo', true, false);

-- Entertainment Products
INSERT INTO products (name, description, category_id, seller_id, price, original_price, stock_quantity, min_quantity, max_quantity, image_url, pin_format, pin_type, region, platform, is_active, is_featured) VALUES
('Netflix Gift Card - $15', 'Netflix streaming service gift card', 2, 2, 15.00, 15.00, 80, 1, 10, 'netflix-15.jpg', 'XXXX-XXXX-XXXX', 'DIGITAL', 'US', 'Netflix', true, true),
('Spotify Premium 1 Month', 'Spotify Premium subscription', 2, 2, 9.99, 9.99, 50, 1, 12, 'spotify-1m.jpg', 'XXXX-XXXX-XXXX', 'DIGITAL', 'GLOBAL', 'Spotify', true, true),
('Disney+ Gift Card - $10', 'Disney+ streaming service gift card', 2, 2, 10.00, 10.00, 40, 1, 10, 'disney-10.jpg', 'XXXX-XXXX-XXXX', 'DIGITAL', 'US', 'Disney+', true, false);

-- Software Products
INSERT INTO products (name, description, category_id, seller_id, price, original_price, stock_quantity, min_quantity, max_quantity, image_url, pin_format, pin_type, region, platform, is_active, is_featured) VALUES
('Microsoft Office 365 Personal 1 Year', 'Microsoft Office 365 Personal subscription', 3, 2, 69.99, 69.99, 15, 1, 3, 'office365-1y.jpg', 'XXXX-XXXX-XXXX', 'DIGITAL', 'GLOBAL', 'Microsoft', true, true),
('Adobe Creative Cloud 1 Month', 'Adobe Creative Cloud subscription', 3, 2, 52.99, 52.99, 10, 1, 12, 'adobe-1m.jpg', 'XXXX-XXXX-XXXX', 'DIGITAL', 'GLOBAL', 'Adobe', true, false);

-- =============================================
-- SAMPLE E-PIN CODES
-- =============================================

-- Steam Wallet Codes
INSERT INTO e_pin_codes (product_id, pin_code, pin_value, is_used, expires_at) VALUES
(1, 'STEA-1234-5678-9012', 'STEA-1234-5678-9012', false, '2025-12-31 23:59:59'),
(1, 'STEA-2345-6789-0123', 'STEA-2345-6789-0123', false, '2025-12-31 23:59:59'),
(1, 'STEA-3456-7890-1234', 'STEA-3456-7890-1234', false, '2025-12-31 23:59:59'),
(2, 'STEA-4567-8901-2345', 'STEA-4567-8901-2345', false, '2025-12-31 23:59:59'),
(2, 'STEA-5678-9012-3456', 'STEA-5678-9012-3456', false, '2025-12-31 23:59:59'),
(3, 'STEA-6789-0123-4567', 'STEA-6789-0123-4567', false, '2025-12-31 23:59:59');

-- PlayStation Store Codes
INSERT INTO e_pin_codes (product_id, pin_code, pin_value, is_used, expires_at) VALUES
(4, 'PSN-1234-5678-9012', 'PSN-1234-5678-9012', false, '2025-12-31 23:59:59'),
(4, 'PSN-2345-6789-0123', 'PSN-2345-6789-0123', false, '2025-12-31 23:59:59'),
(5, 'PSN-3456-7890-1234', 'PSN-3456-7890-1234', false, '2025-12-31 23:59:59'),
(6, 'PSP-4567-8901-2345', 'PSP-4567-8901-2345', false, '2025-12-31 23:59:59');

-- Xbox Live Codes
INSERT INTO e_pin_codes (product_id, pin_code, pin_value, is_used, expires_at) VALUES
(7, 'XBL-1234-5678-9012', 'XBL-1234-5678-9012', false, '2025-12-31 23:59:59'),
(7, 'XBL-2345-6789-0123', 'XBL-2345-6789-0123', false, '2025-12-31 23:59:59'),
(8, 'XGP-3456-7890-1234', 'XGP-3456-7890-1234', false, '2025-12-31 23:59:59');

-- Nintendo eShop Codes
INSERT INTO e_pin_codes (product_id, pin_code, pin_value, is_used, expires_at) VALUES
(9, 'NIN-1234-5678-9012', 'NIN-1234-5678-9012', false, '2025-12-31 23:59:59'),
(9, 'NIN-2345-6789-0123', 'NIN-2345-6789-0123', false, '2025-12-31 23:59:59'),
(10, 'NSO-3456-7890-1234', 'NSO-3456-7890-1234', false, '2025-12-31 23:59:59');

-- Entertainment Codes
INSERT INTO e_pin_codes (product_id, pin_code, pin_value, is_used, expires_at) VALUES
(11, 'NET-1234-5678-9012', 'NET-1234-5678-9012', false, '2025-12-31 23:59:59'),
(11, 'NET-2345-6789-0123', 'NET-2345-6789-0123', false, '2025-12-31 23:59:59'),
(12, 'SPO-3456-7890-1234', 'SPO-3456-7890-1234', false, '2025-12-31 23:59:59'),
(13, 'DIS-4567-8901-2345', 'DIS-4567-8901-2345', false, '2025-12-31 23:59:59');

-- Software Codes
INSERT INTO e_pin_codes (product_id, pin_code, pin_value, is_used, expires_at) VALUES
(14, 'OFF-1234-5678-9012', 'OFF-1234-5678-9012', false, '2025-12-31 23:59:59'),
(15, 'ADB-2345-6789-0123', 'ADB-2345-6789-0123', false, '2025-12-31 23:59:59');

-- =============================================
-- SAMPLE COUPONS
-- =============================================

INSERT INTO coupons (code, name, description, discount_type, discount_value, min_order_amount, max_discount_amount, usage_limit, is_active, valid_from, valid_until) VALUES
('WELCOME10', 'Welcome Discount', '10% off for new users', 'PERCENTAGE', 10.00, 20.00, 50.00, 100, true, '2024-01-01 00:00:00', '2024-12-31 23:59:59'),
('SAVE5', 'Save $5', '$5 off on orders over $25', 'FIXED_AMOUNT', 5.00, 25.00, 5.00, 200, true, '2024-01-01 00:00:00', '2024-12-31 23:59:59'),
('GAMING20', 'Gaming Special', '20% off on gaming products', 'PERCENTAGE', 20.00, 50.00, 100.00, 50, true, '2024-01-01 00:00:00', '2024-12-31 23:59:59');

-- =============================================
-- SAMPLE ORDERS (for testing)
-- =============================================

-- Sample completed order
INSERT INTO orders (order_number, user_id, total_amount, currency, status, payment_method, payment_status, created_at) VALUES
('ORD-2024-001', 3, 25.00, 'USD', 'COMPLETED', 'CREDIT_CARD', 'PAID', '2024-01-15 10:30:00');

-- Sample order items
INSERT INTO order_items (order_id, product_id, quantity, unit_price, total_price) VALUES
(1, 1, 2, 10.00, 20.00),
(1, 4, 1, 5.00, 5.00);

-- Mark some e-pin codes as used for the completed order
UPDATE e_pin_codes SET is_used = true, used_at = '2024-01-15 10:35:00', order_item_id = 1 WHERE id IN (1, 2);
UPDATE e_pin_codes SET is_used = true, used_at = '2024-01-15 10:35:00', order_item_id = 2 WHERE id = 7;

-- Sample payment record
INSERT INTO payments (order_id, payment_method, payment_provider, transaction_id, amount, currency, status, created_at) VALUES
(1, 'CREDIT_CARD', 'STRIPE', 'pi_1234567890', 25.00, 'USD', 'SUCCESS', '2024-01-15 10:30:00');

-- Sample review
INSERT INTO reviews (product_id, user_id, order_id, rating, title, comment, is_verified_purchase, is_approved) VALUES
(1, 3, 1, 5, 'Great service!', 'Received my Steam codes instantly. Will definitely buy again!', true, true);

-- Update product ratings based on reviews
UPDATE products SET rating = 5.00, review_count = 1 WHERE id = 1;

-- =============================================
-- SAMPLE CART ITEMS
-- =============================================

-- Add some items to user's cart
INSERT INTO cart_items (user_id, product_id, quantity) VALUES
(3, 2, 1),
(3, 7, 2);

-- =============================================
-- SAMPLE WISHLIST ITEMS
-- =============================================

-- Add some items to user's wishlist
INSERT INTO wishlist_items (user_id, product_id) VALUES
(3, 3),
(3, 8),
(3, 11);

-- =============================================
-- SAMPLE NOTIFICATIONS
-- =============================================

INSERT INTO notifications (user_id, title, message, type, is_read, data) VALUES
(3, 'Order Completed', 'Your order ORD-2024-001 has been completed successfully!', 'ORDER', false, '{"order_id": 1, "order_number": "ORD-2024-001"}'),
(3, 'New Product Available', 'Check out the new Steam Wallet codes we just added!', 'PROMOTION', false, '{"product_id": 1}'),
(2, 'Low Stock Alert', 'Your Steam Wallet Code - $10 is running low on stock', 'SYSTEM', false, '{"product_id": 1, "stock_quantity": 5}');
