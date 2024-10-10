-- liquibase formatted sql

-- changeset ekamenskikh:1
CREATE TABLE if NOT EXISTS notification_task (
    id BIGINT generated BY DEFAULT AS IDENTITY PRIMARY KEY,
    chat_id BIGINT NOT NULL,
    message_text TEXT NOT NULL,
    notification_date_time TIMESTAMP NOT NULL
)