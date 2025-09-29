# Pizza App

**Pizza App** — микросервисное приложение для заказа пиццы, построенное на современном Java-стеке с использованием Spring, Eureka, Kafka, Keycloak и др.

## Технологический стек

### Общие технологии
- **Java 21**
- **Spring Boot (3.2.x, 3.4.x)**
- **Spring Cloud (2023.0.0, 2024.0.0)**
- **Maven**

### Сервисы

#### service-discovery
- **Spring Cloud Netflix Eureka Server** — сервис обнаружения микросервисов.
- **Spring Cloud Starter Bootstrap**

#### api-gateway
- **Spring Cloud Gateway** — обратный прокси для маршрутизации запросов.
- **Spring Cloud Netflix Eureka Client** — регистрация в Eureka.
- **Spring Security OAuth2** — авторизация через JWT и OAuth2.
- **Spring Boot Starter WebFlux** — реактивный стек.
- **Spring Boot Starter OAuth2 Client** — клиент OAuth2.
- **Spring Boot Starter Data Redis** — хранение и управление сессиями.
- **Spring Session Data Redis/Core**
- **Keycloak (через OAuth2 Resource Server)** — централизованная аутентификация и авторизация пользователей, интеграция с api-gateway.
- **Lombok** — генерация шаблонного кода.

#### user-service, order-service, product-service
- **Spring Boot Starter Web** — создание REST API.
- **Spring Boot Starter Data JPA** — работа с базой данных.
- **Spring Kafka** — обмен сообщениями между микросервисами.
- **PostgreSQL** — основная база данных (runtime).
- **Liquibase** — миграции схемы БД.
- **MapStruct** — маппинг DTO.
- **Spring Cloud OpenFeign** — интеграция с другими сервисами.
- **Lombok** — генерация шаблонного кода.
- **Keycloak Core** (особенно в user-service) — интеграция с системой управления пользователями.
- **Jersey Common** (order-service) — поддержка JAX-RS.

### Тестирование
- **Junit**
- **Spring Boot Starter Test**
- **Reactor Test**

## Интеграция Keycloak

- **api-gateway** интегрируется с Keycloak для проверки JWT, управления сессиями и разграничения доступа.
- **user-service** использует Keycloak для управления пользователями и ролями.
- Все защищённые маршруты проходят проверку через api-gateway с использованием OAuth2 Resource Server и Keycloak.

## Модули

- `service-discovery/` — Eureka Server
- `api-gateway/` — Gateway + OAuth2 + Keycloak
- `order-service/` — Заказы
- `product-service/` — Продукты
- `user-service/` — Пользователи

## Быстрый старт

1. Клонируйте репозиторий:
   ```sh
   git clone https://github.com/Netharus/pizza-app.git
   ```
2. Соберите проект через Maven:
   ```sh
   mvn clean install
   ```
3. Запустите сервисы (локально или через Docker Compose — docker-compose.yml может быть добавлен отдельно).
4. Настройте Keycloak и переменные окружения для интеграции (см. application.yml/application.properties в модулях).
