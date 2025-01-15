package pro.sky.telegrambot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.DeleteMyCommands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурационный класс для настройки Telegram бота.
 * Создает и настраивает экземпляр TelegramBot с использованием токена, указанного в конфигурации.
 */
@Configuration
public class TelegramBotConfiguration {

    /**
     * Токен для доступа к Telegram боту.
     */
    @Value("${telegram.bot.token}")
    private String token;

    /**
     * Создает и настраивает экземпляр TelegramBot.
     *
     * @return Экземпляр TelegramBot.
     */
    @Bean
    public TelegramBot telegramBot() {
        TelegramBot bot = new TelegramBot(token);
        bot.execute(new DeleteMyCommands());
        return bot;
    }

}
