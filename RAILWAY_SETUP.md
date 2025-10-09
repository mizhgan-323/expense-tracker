# 🚀 Настройка Railway + Supabase

## ✅ Что уже сделано:

- Исправлен синтаксис build.gradle ✅
- Сборка проходит успешно ✅
- Добавлен Spring Boot Actuator ✅

## 🔧 Что нужно сделатьь:

### 1. Получите данные подключения к Supabase:

1. Зайдите в ваш проект Supabase
2. Settings → Database
3. Connection Info - скопируйте:
   - **Host**: `db.[ваш-проект-id].supabase.co`
   - **Database name**: `postgres`
   - **Port**: `5432`
   - **Username**: `postgres`
   - **Password**: ваш пароль

### 2. Настройте переменные окружения в Railway:

1. Зайдите в ваш проект Railway
2. Settings → Variables
3. Добавьте переменные:

```
DATABASE_URL=jdbc:postgresql://db.zjqflqalrypsfkkoowkx.supabase.co:5432/postgres
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=misasa123
```

### 3. ✅ Ваши данные подключения:

- **Host**: `db.zjqflqalrypsfkkoowkx.supabase.co`
- **Port**: `5432`
- **Database**: `postgres`
- **Username**: `postgres`
- **Password**: `misasa123`

### 4. После настройки переменных:

- Railway автоматически перезапустит приложение
- Healthcheck должен пройти успешно
- Приложение будет доступно по вашему домену Railway

## 🔍 Проверка:

- **Healthcheck**: `https://[ваш-домен].up.railway.app/actuator/health`
- **API документация**: `https://[ваш-домен].up.railway.app/swagger-ui.html`

## ❗ Если проблемы остаются:

1. Проверьте логи в Railway Dashboard
2. Убедитесь, что пароль Supabase правильный
3. Проверьте, что база данных Supabase доступна
