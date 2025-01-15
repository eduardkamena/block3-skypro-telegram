package pro.sky.telegrambot.service;

/**
 * Интерфейс сервиса для отправки сообщений через Telegram бота.
 */
public interface MessageSenderService {

    /**
     * Отправляет сообщение в указанный чат.
     *
     * @param chatID Идентификатор чата.
     * @param messageText Текст сообщения.
     */
    void sendMessage(Long chatID, String messageText);

}
