# newspaperMaven

Maven multi-module Spring Boot 4 приложение (модули `dao`/`services`/`web`).

## Локальный запуск

### 1. Требования
- JDK 21
- Maven
- Docker (для локальной MySQL)

### 2. Поднять MySQL
```
docker compose up -d
```
Создаст контейнер `newspaper-mysql` и базу `newspaper`. Пароль root берётся из `.env` (по умолчанию `root`, см. `.env.example`).

### 3. Переменные окружения
Перед запуском приложения задать (PowerShell):
```powershell
$env:JAVA_HOME="C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="root"
$env:ADMIN_USERNAME="admin"
$env:ADMIN_PASSWORD_HASH="<bcrypt-хэш>"
```
`DB_PASSWORD` обязательно должен совпадать с `MYSQL_ROOT_PASSWORD` из `.env` — иначе приложение упадёт на старте с access denied (по умолчанию `DB_PASSWORD` — пустая строка).

`ADMIN_PASSWORD_HASH` генерируется так (Java):
```java
new BCryptPasswordEncoder().encode("ваш-пароль")
```

### 4. Собрать и запустить
```
mvn clean install
mvn -pl web spring-boot:run
```
Либо через собранный jar:
```
java -jar web/target/web.jar
```

Схема БД создаётся автоматически через Liquibase при старте приложения (`web/src/main/resources/db/changelog/`), Hibernate (`ddl-auto: validate`) после этого проверяет соответствие схемы entity-классам.

Приложение поднимется на `http://localhost:8080`.

### 5. Тесты
```
mvn clean verify
```
Работает без запущенного Docker — все тесты используют моки и не обращаются к реальной БД.
