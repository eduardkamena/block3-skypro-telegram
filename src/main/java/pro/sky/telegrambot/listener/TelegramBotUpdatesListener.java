package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import pro.sky.telegrambot.service.MessageSenderService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс, обрабатывающий входящие обновления от Telegram бота.
 * Реагирует на команды и сообщения пользователей, сохраняет задачи уведомлений.
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final String WELCOME_MESSAGE = "Добро пожаловать в бот, отправляющий Вам напоминания!\n\n"
            + "Введите /info";
    private final String EXAMPLE_MESSAGE = "Введите задачу в формате <01.01.2022 20:00 Сделать домашнюю работу>";

    // Паттерн, которая поможет распознать дату и текст, проверка на regex101.com
    private final Pattern NOTIFICATION_MESSAGE_FORMAT = Pattern.compile(
            "(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)"
    );

    private final DateTimeFormatter NOTIFICATION_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern(
            "dd.MM.yyyy HH:mm"
    );

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private MessageSenderService messageSenderService;

    @Autowired
    private NotificationTaskRepository notificationTaskRepository;

    /**
     * Инициализация слушателя обновлений.
     */
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Обрабатывает входящие обновления от Telegram бота.
     *
     * @param updates Список обновлений.
     * @return Код подтверждения обработки обновлений.
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            Message message = update.message();

            if (message == null) {
                logger.error("Received unsupported message type {}", update);
                return;
            }

            Long chatId = message.chat().id();
            String messageText = message.text();

            if ("/info".equals(messageText)) {
                messageSenderService.sendMessage(chatId, EXAMPLE_MESSAGE);
            } else if ("/start".equals(messageText)) {
                messageSenderService.sendMessage(chatId, WELCOME_MESSAGE);
            } else {
                Matcher messageMatcher = NOTIFICATION_MESSAGE_FORMAT.matcher(messageText);

                if (messageMatcher.matches()) {

                    // Сохранение и вычленение по группам сообщения от пользователя
                    // группы можно посмотреть на regex101.com
                    NotificationTask notificationTask = new NotificationTask();
                    notificationTask.setChatId(chatId);
                    notificationTask.setMessageText(messageMatcher.group(3));
                    notificationTask.setNotificationDateTime(LocalDateTime.parse(
                            messageMatcher.group(1),
                            NOTIFICATION_DATE_TIME_FORMAT
                    ));

                    notificationTaskRepository.save(notificationTask);

                    logger.info("Message \"{}\" was saved successfully", notificationTask);

                    messageSenderService.sendMessage(chatId, "Напоминание успешно сохранено!\n\n"
                            + "Дата и время напоминания: " + notificationTask.getNotificationDateTime());
                } else {
                    logger.warn("Uncorrected message format {}", messageText);
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
