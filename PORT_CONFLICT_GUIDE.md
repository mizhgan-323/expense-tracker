# Решение проблемы "Port 8080 was already in use"

## Проблема

При запуске приложения возникает ошибка:

```
Web server failed to start. Port 8080 was already in use.
```

Это означает, что порт 8080 уже используется другим процессом.

## Решения

### 1. Быстрое решение - остановить процесс на порту 8080

#### Через командную строку:

```bash
# Найти процесс, использующий порт 8080
netstat -ano | findstr :8080

# Остановить процесс по PID (замените XXXX на реальный PID)
taskkill /PID XXXX /F
```

#### Через PowerShell:

```powershell
# Найти процесс на порту 8080
netstat -ano | findstr :8080

# Остановить процесс по PID
Stop-Process -Id XXXX -Force
```

### 2. Альтернативное решение - изменить порт приложения

Добавьте в `src/main/resources/application.properties`:

```properties
# Изменить порт на 8081
server.port=8081
```

Тогда Swagger будет доступен по адресу: http://localhost:8081/swagger-ui.html

### 3. Проверка всех Java процессов

```powershell
# Посмотреть все Java процессы
Get-Process -Name "java"

# Остановить все Java процессы (осторожно!)
Get-Process -Name "java" | Stop-Process -Force
```

### 4. Автоматический скрипт для очистки портов

Создайте файл `clear-ports.ps1`:

```powershell
# Остановить все процессы на портах 8080, 8081, 8082
$ports = @(8080, 8081, 8082)

foreach ($port in $ports) {
    $processes = Get-NetTCPConnection -LocalPort $port -ErrorAction SilentlyContinue | Select-Object -ExpandProperty OwningProcess
    foreach ($pid in $processes) {
        try {
            Stop-Process -Id $pid -Force
            Write-Host "Остановлен процесс $pid на порту $port"
        } catch {
            Write-Host "Не удалось остановить процесс $pid на порту $port"
        }
    }
}
```

Запустите скрипт:

```powershell
.\clear-ports.ps1
```

## Профилактика

### 1. Всегда останавливайте предыдущие запуски

- В IDE нажмите кнопку "Stop" перед новым запуском
- В командной строке используйте Ctrl+C для остановки

### 2. Используйте разные порты для разных проектов

```properties
# Проект 1
server.port=8080

# Проект 2
server.port=8081

# Проект 3
server.port=8082
```

### 3. Настройте IDE для автоматической остановки

В IntelliJ IDEA:

1. File → Settings → Build, Execution, Deployment → Build Tools → Gradle
2. Поставьте галочку "Stop Gradle daemon when IDE is closed"

## Проверка успешного запуска

После решения проблемы с портами приложение должно запускаться успешно, и вы сможете открыть:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Documentation**: http://localhost:8080/api-docs

## Команды для проверки

```powershell
# Проверить, что порт свободен
netstat -ano | findstr :8080

# Проверить доступность Swagger
Invoke-WebRequest -Uri "http://localhost:8080/swagger-ui.html" -UseBasicParsing

# Проверить доступность API
Invoke-WebRequest -Uri "http://localhost:8080/api-docs" -UseBasicParsing
```

Если команды возвращают StatusCode 200, значит всё работает корректно!
