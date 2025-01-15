package pro.sky.telegrambot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.service.MessageSenderService;

/**
 * Сервис для отправки сообщений через Telegram бота.
 */
@Component
public class MessageSenderServiceImpl implements MessageSenderService {

    private final Logger logger = LoggerFactory.getLogger(MessageSenderServiceImpl.class);

    @Autowired
    private TelegramBot telegramBot;

    /**
     * Отправляет сообщение в указанный чат.
     *
     * @param chatID Идентификатор чата.
     * @param messageText Текст сообщения.
     */
    @Override
    public void sendMessage(Long chatID, String messageText) {
        SendMessage sendMessage = new SendMessage(chatID, messageText);
        SendResponse sendResponse = telegramBot.execute(sendMessage);

        if (sendResponse.isOk()) {
            logger.info("Message for user {} with text \"{}\" was sent successfully", chatID, messageText);
        } else {
            logger.error("Unsuccessfully sending message for user {} with text \"{}\"", chatID, messageText);
        }
    }

}
