# Swagger/OpenAPI Документация для Expense Tracker

## Обзор

В ваш проект Expense Tracker была успешно интегрирована Swagger/OpenAPI документация. Теперь у вас есть интерактивная документация API, которая позволяет тестировать все endpoints прямо из браузера.

## Доступ к документации

После запуска приложения документация будет доступна по следующим URL:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

## Запуск приложения

```bash
./gradlew bootRun
```

## Что было добавлено

### 1. Зависимости

- Добавлена зависимость `springdoc-openapi-starter-webmvc-ui` в `build.gradle`

### 2. Конфигурация

- Настроены параметры Swagger в `application.properties`
- Создан конфигурационный класс `OpenApiConfig.kt`

### 3. Документация API

Добавлены подробные аннотации для всех контроллеров:

#### Categories API (`/api/categories`)

- `GET /api/categories` - Получить все категории
- `GET /api/categories/type/{type}` - Получить категории по типу
- `GET /api/categories/expense` - Получить категории расходов
- `GET /api/categories/income` - Получить категории доходов
- `GET /api/categories/{id}` - Получить категорию по ID
- `POST /api/categories` - Создать новую категорию
- `PUT /api/categories/{id}` - Обновить категорию
- `DELETE /api/categories/{id}` - Удалить категорию
- `PATCH /api/categories/{id}/deactivate` - Деактивировать категорию
- `PATCH /api/categories/{id}/activate` - Активировать категорию

#### Transactions API (`/api/transactions`)

- `GET /api/transactions` - Получить все транзакции
- `GET /api/transactions/{id}` - Получить транзакцию по ID
- `POST /api/transactions` - Создать новую транзакцию
- `PUT /api/transactions/{id}` - Обновить транзакцию
- `DELETE /api/transactions/{id}` - Удалить транзакцию
- `GET /api/transactions/type/{type}` - Получить транзакции по типу

### 4. Схемы данных

Добавлены подробные описания для всех DTO и Entity классов:

- `CategoryDto` - DTO для категории
- `CreateCategoryRequest` - Запрос на создание категории
- `UpdateCategoryRequest` - Запрос на обновление категории
- `Category` - Entity категории
- `Transaction` - Entity транзакции
- `CategoryType` и `TransactionType` - Enum'ы типов

## Использование Swagger UI

1. Запустите приложение: `./gradlew bootRun`
2. Откройте браузер и перейдите на http://localhost:8080/swagger-ui.html
3. Вы увидите интерактивную документацию с двумя основными разделами:

   - **Categories** - API для управления категориями
   - **Transactions** - API для управления транзакциями

4. Для тестирования любого endpoint:
   - Нажмите на endpoint
   - Нажмите "Try it out"
   - Заполните необходимые параметры
   - Нажмите "Execute"
   - Посмотрите ответ сервера

## Особенности

- Все endpoints имеют подробные описания на русском языке
- Указаны примеры данных для всех полей
- Документированы все возможные коды ответов (200, 201, 400, 404, 204)
- Поддержка интерактивного тестирования API
- Автоматическая генерация схем данных

## Настройки

Конфигурация Swagger находится в `src/main/resources/application.properties`:

```properties
# Swagger/OpenAPI Configuration
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.operationsSorter=method
springdoc.swagger-ui.tagsSorter=alpha
springdoc.swagger-ui.try-it-out-enabled=true
```

Вы можете изменить эти настройки по своему усмотрению.

## Заключение

Теперь у вас есть полноценная документация API с возможностью интерактивного тестирования. Это значительно упростит разработку, тестирование и интеграцию с вашим API.
