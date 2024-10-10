package pro.sky.telegrambot.service;

public interface MessageSenderService {

    void sendMessage(Long chatID, String messageText);

}
