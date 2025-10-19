# 🌸💨 Lattaf Perfume Backend

Lataf Perfume Backend is a modern e-commerce backend system built with 
a scalable and resilient microservices design. The architecture is designed to handle product management, order processing, inventory and notifications 
efficiently while ensuring high availability, observability, and secure communication across services.
The system is containerized with Docker and orchestrated via Kubernetes, ensuring
flexibility in deployment and scaling.

## 🧰 Technologies

- **Backend Framework** - Java 17m Spring Boot 3, Spring Cloud
- **Authenication** - Kinde (OAuth2, OpenID Connect)
- **Databases** - MySQL, MongoDB
- **Caching** - Redis
- **Message Broker** - Apache Kafka
- **Service Discovery** - Eureka
- **Containerization & Orchestration** - Docker, Kubernetes
- **Observability && Monitoring** - Prometheus, Grafana, Loki, Tempo, OpenTelemetry
- **API Gateway** - Spring Cloud Gateway

## 🦄 Features

- Modular microservice-based architecture for scalability and maintainability
- Secure authentication and authorization using OAuth2
- Synchronous and asynchronous communication between services
- Event-driven notifications through Kafka
- Database migration via Flyway
- Caching layer for improved performance and reduced latency
- Centralized logging and monitoring for observability and debugging
- Containerized deployment with Docker and Kubernetes

## 🧩 Architecture

<img width="1897" height="1070" alt="Lattaf Perfume Microservices Architecture" src="https://github.com/user-attachments/assets/7825ec02-ffb7-4a4a-8fcd-75d10b4f5090" />

The client communicates with the system through the API Gateway, which routes requests to different microservices and interacts with the Authentication Server (Kinde) for secure access control.

- The Product Service manages product details and stores data in MongoDB.

- The Order Service, backed by MySQL and Redis caching, handles order placement and communication with other services.

- The Inventory Service synchronizes product stock levels through synchronous communication with the order service.

- The Notification Service listens for events through Apache Kafka for asynchronous communication, sending notifications after successful transactions.

- Eureka Server provides service discovery to enable dynamic scaling and interaction among microservices.

- Prometheus, Grafana, Grafana Loki, Grafana Tempo, and OpenTelemetry provide observability, monitoring, and distributed tracing across the system.

- Circuit Breaker patterns are implemented to ensure fault tolerance and prevent cascading failures across services.

## 🌟 Services & Endpoints

- Product Service API

<img width="1319" height="809" alt="product service swagger" src="https://github.com/user-attachments/assets/46bb0b57-330c-405b-84ff-8d1ee848fbff" />

- Order Service API

<img width="1390" height="808" alt="order service swagger" src="https://github.com/user-attachments/assets/903e2a2e-2975-4be5-a3eb-d84ffa43e3d3" />

- Inventory Service API

<img width="1335" height="749" alt="inventory service swagger" src="https://github.com/user-attachments/assets/0c448d77-c219-4908-84d7-c6e01dc87101" />

## 💭 How can it be improved?

- Host all microservices on a managed cloud platform like AWS, Azure or GCP using Kubernetes
- Integrate Spring Cloud Config or Vault to securely manage environment-specific configurations and credentials in the cloud
- Implement Github Actions or Jenkins pipelines for automated cloud deployments.

## 🍿 Video

#### 🧭 System Flow Overview, Product to Order Lifecycle

The following flow demonstrates how the Lattaf Perfume Microservices Architecture handles product creation, inventory synchronization, and customer ordering from end to end:

#### 🧱 Product Creation

- The admin or backend system sends a `POST /api/product request` to the Product Service to create a new product.

- The product information (name, price, description, etc.) is stored in MongoDB.

#### 📦 Inventory Synchronization

- Once the product is successfully created, a `POST /api/inventory` request is triggered automatically or manually to register the initial stock level in the Inventory Service.

- The inventory data is stored in MySQL, ensuring stock levels are tracked per product.

#### 🏠 Display on Homepage (Featured Products)

- The Frontend (Next.js) calls `GET /api/product/featured` to fetch and display featured products on the homepage.

- The response comes from the Product Service, showing items available for users to browse.

#### 🛍️ Product Detail View

- When a customer navigates to a specific product page, the frontend sends `GET /api/product/{id}`.

- The Product Service returns full details (name, price, description, etc.) for that specific product.

#### 🧠 Cart and Inventory Cache

- When the product is added to the cart, the system retrieves the latest stock level from the Inventory Service `GET /api/inventory/{id}` to ensure accurate availability.

- The inventory data is then temporarily stored in Redis for faster access and reduced database load during the checkout process.

#### 💳 Order Submission

- When the customer proceeds to checkout, the frontend sends `POST /api/order` to the Order Service, including the product IDs, quantities, and user details.

- The Order Service validates stock availability via Redis and the Inventory Service.

- After successful validation, the order details are stored in MySQL. The Inventory Service updates the remaining stock. A message is published to Apache Kafka for asynchronous communication with the Notification Service.

#### 📧 Notification Handling

- The Notification Service listens to Kafka events and sends an email or confirmation message to the customer once the order is successfully placed.

https://github.com/user-attachments/assets/4b556f82-412c-467d-9da2-2def413b692e

https://github.com/user-attachments/assets/c4d79a62-6049-4a8e-8c3e-70cfd4cff2cd

https://github.com/user-attachments/assets/a51643e7-2a5b-47ef-a9aa-939ede295b6a

https://github.com/user-attachments/assets/66a32f8b-9fb7-4244-bedd-e56062814862

https://github.com/user-attachments/assets/25fc3e18-e16b-4517-b478-939fd011d6fa

https://github.com/user-attachments/assets/7b1b4283-82f2-4a65-b7b0-068c31ad9e81

https://github.com/user-attachments/assets/d102017f-4ed1-4703-944e-18b5a63a89da

https://github.com/user-attachments/assets/42a9d144-f6e9-46ec-8988-44a604b0e02a




