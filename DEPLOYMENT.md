# Инструкция по деплою в Railway с Supabase

## Настройка переменных окружения в Railway

После создания проекта в Railway, добавьте следующие переменные окружения:

### База данных Supabase

```
DATABASE_URL=jdbc:postgresql://db.[ваш-проект-id].supabase.co:5432/postgres
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=[ваш-пароль-от-supabase]
```

### Как получить данные подключения к Supabase:

1. Зайдите в ваш проект Supabase
2. Перейдите в Settings → Database
3. Скопируйте:

   - **Host**: `db.[ваш-проект-id].supabase.co`
   - **Database name**: `postgres`
   - **Port**: `5432`
   - **Username**: `postgres`
   - **Password**: ваш пароль

4. Сформируйте DATABASE_URL:
   ```
   jdbc:postgresql://db.[ваш-проект-id].supabase.co:5432/postgres
   ```

### Дополнительные переменные (опционально)

```
SPRING_PROFILES_ACTIVE=prod
PORT=8080
```

## Проверка деплоя

После настройки переменных окружения:

1. Сделайте commit и push изменений
2. Railway автоматически пересоберет и задеплоит приложение
3. Проверьте логи в Railway Dashboard

## Swagger UI

После успешного деплоя API документация будет доступна по адресу:
`https://[ваш-домен-railway].up.railway.app/swagger-ui.html`
