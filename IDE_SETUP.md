# Настройка IDE для Expense Tracker

## Проблема

Если при запуске в IDE возникает ошибка:

```
Error: LinkageError occurred while loading main class money_tracker.expense_tracker.ExpenseTrackerApplicationKt
java.lang.UnsupportedClassVersionError: money_tracker/expense_tracker/ExpenseTrackerApplicationKt has been compiled by a more recent version of the Java Runtime (class file version 65.0), this version of the Java Runtime only recognizes class file versions up to 61.0
```

Это означает, что IDE использует более старую версию Java, чем та, с которой был скомпилирован проект.

## Решение

### 1. Настройка IntelliJ IDEA

1. **Откройте File → Project Structure (Ctrl+Alt+Shift+S)**
2. **В разделе Project:**
   - Project SDK: выберите Java 17 или выше
   - Project language level: 17
3. **В разделе Modules:**
   - Убедитесь, что Language level установлен в 17
4. **В разделе SDKs:**
   - Если Java 17 не отображается, добавьте её:
     - Нажмите "+" → Add JDK
     - Выберите папку с Java 17 (обычно `C:\Program Files\Java\jdk-17.x.x`)

### 2. Настройка Run Configuration

1. **Откройте Run → Edit Configurations**
2. **Выберите вашу конфигурацию или создайте новую:**
   - Main class: `money_tracker.expense_tracker.ExpenseTrackerApplicationKt`
   - Module: expense-tracker.main
   - JRE: выберите Java 17 или выше

### 3. Настройка Gradle

1. **Откройте File → Settings (Ctrl+Alt+S)**
2. **Перейдите в Build, Execution, Deployment → Build Tools → Gradle**
3. **Убедитесь, что:**
   - Gradle JVM: выберите Java 17 или выше
   - Build and run using: Gradle
   - Run tests using: Gradle

### 4. Очистка и пересборка

1. **Выполните Gradle refresh:**

   - Откройте Gradle панель (View → Tool Windows → Gradle)
   - Нажмите кнопку "Refresh Gradle Project" (🔄)

2. **Очистите проект:**

   - Build → Clean Project

3. **Пересоберите проект:**
   - Build → Rebuild Project

### 5. Проверка версий Java

Выполните в терминале IDE:

```bash
java -version
./gradlew -version
```

Убедитесь, что обе команды показывают Java 17 или выше.

## Альтернативное решение через командную строку

Если настройка IDE не помогает, можно запускать приложение через Gradle:

```bash
# Очистка проекта
./gradlew clean

# Пересборка
./gradlew build

# Запуск
./gradlew bootRun
```

## Проверка успешного запуска

После правильной настройки приложение должно запускаться без ошибок, и вы сможете открыть:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Documentation**: http://localhost:8080/api-docs

## Примечания

- Проект настроен на Java 17 для максимальной совместимости
- Если у вас установлена только Java 24, рекомендуется также установить Java 17
- Все зависимости совместимы с Java 17
- Swagger документация работает корректно с Java 17

## Версии Java и их номера

- Java 8: 52
- Java 11: 55
- Java 17: 61 ✅ (используется в проекте)
- Java 21: 65
- Java 24: 68
