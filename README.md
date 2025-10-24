# E-Pin Marketplace Backend

A comprehensive microservices-based backend system for an E-Pin marketplace platform built with Spring Boot and Spring Cloud. This platform enables users to buy and sell digital products like gaming credits, gift cards, software licenses, and other digital goods.

## 🏗️ Architecture Overview

This project follows a microservices architecture pattern with the following components:

### Core Services

- **API Gateway** - Central entry point for all client requests
- **Discovery Service** - Service registry using Eureka
- **User Service** - User management, authentication, and authorization
- **Product Service** - Product catalog and inventory management
- **Category Service** - Product categorization and hierarchy
- **Order Service** - Order processing, cart, and wishlist management
- **Payment Service** - Payment processing and transaction management
- **Notification Service** - Real-time notifications and messaging
- **Coupon Service** - Discount codes and promotional management

### Infrastructure

- **PostgreSQL** - Primary database for data persistence
- **Redis** - Caching and session management
- **RabbitMQ** - Message queuing for asynchronous communication
- **Docker** - Containerization and orchestration

## 🚀 Technology Stack

- **Java 17**
- **Spring Boot 3.5.6**
- **Spring Cloud 2025.0.0**
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data access layer
- **PostgreSQL 15** - Primary database
- **Redis 7** - Caching layer
- **RabbitMQ 3** - Message broker
- **Docker & Docker Compose** - Containerization
- **Maven** - Build and dependency management

## 📋 Prerequisites

Before running this project, ensure you have the following installed:

- Java 17 or higher
- Maven 3.6 or higher
- Docker and Docker Compose
- Git

## 🛠️ Quick Start

### 1. Clone the Repository

```bash
git clone <repository-url>
cd epin-backend
```

### 2. Start Infrastructure Services

```bash
docker-compose up -d
```

This will start:
- PostgreSQL database on port 5432
- Redis on port 6379
- RabbitMQ on port 5672 (Management UI on 15672)
- PgAdmin on port 5050

### 3. Database Setup

The database schema will be automatically created when PostgreSQL starts. You can also manually run:

```bash
psql -h localhost -U epin_user -d epin_marketplace -f schema.sql
```

### 4. Start Microservices

Start the services in the following order:

```bash
# 1. Start Discovery Service
cd discovery-service
mvn spring-boot:run

# 2. Start API Gateway
cd ../api-gateway
mvn spring-boot:run

# 3. Start Core Services (in any order)
cd ../user-service
mvn spring-boot:run

cd ../category-service
mvn spring-boot:run

cd ../product-service
mvn spring-boot:run

cd ../order-service
mvn spring-boot:run

cd ../payment-service
mvn spring-boot:run

cd ../notification-service
mvn spring-boot:run

cd ../coupon-service
mvn spring-boot:run
```

## 🔧 Configuration

### Service Ports

| Service | Port | Description |
|---------|------|-------------|
| Discovery Service | 8761 | Eureka Server |
| API Gateway | 8080 | Main entry point |
| User Service | 8081 | User management |
| Category Service | 8082 | Category management |
| Product Service | 8083 | Product catalog |
| Order Service | 8084 | Order processing |
| Payment Service | 8085 | Payment processing |
| Notification Service | 8086 | Notifications |
| Coupon Service | 8087 | Coupon management |

### Database Configuration

Default database credentials:
- **Database**: epin_marketplace
- **Username**: epin_user
- **Password**: epin_password
- **Host**: localhost
- **Port**: 5432

### Redis Configuration

- **Host**: localhost
- **Port**: 6379
- **No password required**

### RabbitMQ Configuration

- **Host**: localhost
- **Port**: 5672
- **Management UI**: http://localhost:15672
- **Username**: guest
- **Password**: guest

## 📚 API Documentation

### API Gateway Endpoints

All API requests should be made through the API Gateway at `http://localhost:8080`:

#### User Management
- `GET /api/users` - Get all users
- `POST /api/users/register` - Register new user
- `POST /api/users/login` - User login
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

#### Product Management
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

#### Category Management
- `GET /api/categories` - Get all categories
- `GET /api/categories/{id}` - Get category by ID
- `POST /api/categories` - Create new category
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}` - Delete category

#### Order Management
- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID
- `POST /api/orders` - Create new order
- `PUT /api/orders/{id}` - Update order status

#### Cart & Wishlist
- `GET /api/cart` - Get user's cart
- `POST /api/cart/add` - Add item to cart
- `DELETE /api/cart/{id}` - Remove item from cart
- `GET /api/wishlist` - Get user's wishlist
- `POST /api/wishlist/add` - Add item to wishlist

#### Payment Processing
- `POST /api/payments/process` - Process payment
- `GET /api/payments/{id}` - Get payment status
- `POST /api/payments/refund` - Process refund

#### Notifications
- `GET /api/notifications` - Get user notifications
- `PUT /api/notifications/{id}/read` - Mark notification as read

## 🗄️ Database Schema

The database includes the following main entities:

- **Users** - User accounts and profiles
- **Categories** - Product categories and subcategories
- **Products** - E-Pin products and digital goods
- **Orders** - Customer orders and transactions
- **Order Items** - Individual items within orders
- **E-Pin Codes** - Digital codes and licenses
- **Payments** - Payment transactions
- **Reviews** - Product reviews and ratings
- **Cart Items** - Shopping cart contents
- **Wishlist Items** - User wishlists
- **Coupons** - Discount codes and promotions
- **Notifications** - System and user notifications

## 🔐 Security Features

- JWT-based authentication
- Role-based access control (USER, SELLER, ADMIN)
- Password encryption using BCrypt
- CORS configuration for cross-origin requests
- Input validation and sanitization

## 🧪 Testing

Run tests for individual services:

```bash
# Test specific service
cd user-service
mvn test

# Test all services
mvn test
```

## 📦 Deployment

### Docker Deployment

```bash
# Build all services
docker-compose build

# Start all services
docker-compose up -d

# View logs
docker-compose logs -f
```

### Production Considerations

- Configure proper database credentials
- Set up SSL/TLS certificates
- Configure load balancers
- Set up monitoring and logging
- Configure backup strategies

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

For support and questions:

- Create an issue in the repository
- Contact the development team
- Check the documentation

## 🔄 Version History

- **v1.0.0** - Initial release with core microservices
- **v1.1.0** - Added payment processing and notifications
- **v1.2.0** - Enhanced security and API documentation

---

**Note**: This is a development version. For production deployment, additional configuration and security measures are required.