# newspaperMaven

Maven multi-module Spring Boot 4 приложение (модули `dao`/`services`/`web`).

## Локальный запуск через Docker Compose (рекомендуется)

### 1. Требования
- Docker

### 2. Переменные окружения
```
cp .env.example .env
```
В `.env` обязательно задать `ADMIN_PASSWORD_HASH` (по умолчанию пустой) — без него приложение не стартует. Хэш генерируется так (Java):
```java
new BCryptPasswordEncoder().encode("ваш-пароль")
```
Хэш нужно взять в одинарные кавычки (`ADMIN_PASSWORD_HASH='$2a$...'`) — иначе docker compose попытается интерпретировать `$` внутри как ссылки на переменные и испортит значение.

`MYSQL_ROOT_PASSWORD` и `ADMIN_USERNAME` уже имеют рабочие значения по умолчанию (`root`/`admin`).

### 3. Запуск
```
docker compose up -d --build
```
Одной командой поднимаются оба сервиса:
- `mysql` — база `newspaper`, данные сохраняются в volume `newspaper_mysql_data`
- `app` — собирается из исходников через `Dockerfile` (multi-stage: Maven-сборка → JRE-образ), стартует после того, как MySQL пройдёт healthcheck

Схема БД создаётся автоматически через Liquibase при старте приложения (`web/src/main/resources/db/changelog/`), Hibernate (`ddl-auto: validate`) после этого проверяет соответствие схемы entity-классам.

Приложение будет доступно на `http://localhost:8080`.

Пересобрать образ приложения после изменения кода: `docker compose up -d --build`. Полный сброс (включая данные БД): `docker compose down -v`.

## Локальный запуск без Docker (для разработки)

### 1. Требования
- JDK 21
- Maven
- Docker — только для контейнера MySQL (сам Spring Boot запускается локально через Maven/`java -jar`)

### 2. Поднять MySQL
```
docker compose up -d mysql
```
Пароль root берётся из `.env` (см. `.env.example`).

### 3. Переменные окружения
```powershell
$env:JAVA_HOME="C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="root"
$env:ADMIN_USERNAME="admin"
$env:ADMIN_PASSWORD_HASH="<bcrypt-хэш>"
```
`DB_PASSWORD` обязательно должен совпадать с `MYSQL_ROOT_PASSWORD` из `.env` — иначе приложение упадёт на старте с access denied (по умолчанию `DB_PASSWORD` — пустая строка). `DB_HOST`/`DB_PORT` не нужны — по умолчанию `localhost:3306`, что подходит для запуска вне контейнера.

### 4. Собрать и запустить
```
mvn clean install
mvn -pl web spring-boot:run
```
Либо через собранный jar:
```
java -jar web/target/web.jar
```

## Тесты
```
mvn clean verify
```
Работает без запущенного Docker — все тесты используют моки и не обращаются к реальной БД.
