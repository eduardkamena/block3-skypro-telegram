package pro.sky.telegrambot.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import pro.sky.telegrambot.service.MessageSenderService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class NotificationJob {

    private final Logger logger = LoggerFactory.getLogger(NotificationJob.class);

    @Autowired
    private NotificationTaskRepository notificationTaskRepository;

    @Autowired
    private MessageSenderService messageSenderService;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES) // Автоапдэйт каждую 1 минуту
    public void sendNotification() {

        LocalDateTime currentDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        logger.info("Job start for date time {}", currentDateTime);

        // Поиск напоминания по дате и времени
        List<NotificationTask> notificationTasks = notificationTaskRepository.findAllByNotificationDateTime(currentDateTime);

        logger.info("{} tasks has been found", notificationTasks.size());

        // Итерируемся по всем найденным напоминаниям и отправляем пользователю текст его напоминания(й)
        for (NotificationTask notificationTask : notificationTasks) {
            messageSenderService.sendMessage(
                    notificationTask.getChatId(),
                    "Напоминание! - " + notificationTask.getMessageText()
            );

            logger.info("Notification with id {} has been successfully sent", notificationTask.getId());

            // Удаляем отправленное напоминание из базы
            notificationTaskRepository.delete(notificationTask);

            logger.info("Notification with id {} has been deleted", notificationTask.getId());
        }

        logger.info("Job finished");
    }

}
