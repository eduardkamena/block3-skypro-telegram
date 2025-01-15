# Telegram Notification Bot

Этот проект представляет собой Telegram бота, созданного с использованием Spring Boot. 
Бот позволяет пользователям создавать напоминания, которые отправляются в указанное время.

## Основные функции

- Создание напоминаний с указанием даты, времени и текста.
- Автоматическая отправка напоминаний в указанное время.
- Удобный интерфейс для взаимодействия с ботом.

## Технологии

- **Spring Boot** — фреймворк для создания приложений на Java.
- **Telegram Bot API** — библиотека для взаимодействия с Telegram API.
- **PostgreSQL** — реляционная база данных для хранения задач.
- **Liquibase** — инструмент для управления изменениями в базе данных.

## Требования

- Java 11
- Maven 3.x
- PostgreSQL

## Установка и запуск

1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/eduardkamena/telegramNotificationBot.git
   ```
   
2. Настройте базу данных:
   - Создайте базу данных в PostgreSQL.
   - Обновите настройки в `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/ваша-база-данных
   spring.datasource.username=ваш-username
   spring.datasource.password=ваш-пароль
   ```
   
3. Соберите проект с помощью Maven:
   ```bash
   mvn clean install
   ```
   
4. Запустите приложение:
   ```bash
   mvn spring-boot:run
   ```
   
5. Настройте Telegram бота:
   - Создайте бота через [BotFather](https://core.telegram.org/bots#botfather).
   - Укажите токен бота в `application.properties`:
   ```properties
   telegram.bot.token=ваш-токен
   ```
   
## Использование
После запуска бота вы можете взаимодействовать с ним через Telegram. Вот основные команды:

- `/start` — приветственное сообщение.
- `/info` — пример формата для создания напоминания.
- `01.01.2023 20:00 Сделать домашнюю работу` — создание напоминания на указанное время.

## Структура проекта
```
telegram-bot/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── pro/sky/telegrambot/
│   │   │       ├── configuration/
│   │   │       ├── entity/
│   │   │       ├── job/
│   │   │       ├── listener/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── TelegramBotApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── liquibase/
│   └── test/
│       └── java/
│           └── pro/sky/telegrambot/
├── pom.xml
└── README.md
```

## Тестирование
Для запуска тестов выполните команду:
```bash
mvn test
```

## Дополнительно
- Это простая версия бота, показывающая базовый функционал.
- Планируется развитие проекта и доработка.