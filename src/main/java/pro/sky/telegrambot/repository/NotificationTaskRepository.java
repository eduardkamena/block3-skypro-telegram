package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.entity.NotificationTask;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий для работы с сущностью NotificationTask.
 * Предоставляет методы для поиска задач уведомлений по времени.
 */
@Repository
public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {

    /**
     * Находит все задачи уведомлений, которые должны быть выполнены в указанное время.
     *
     * @param notificationDateTime Время уведомления.
     * @return Список задач уведомлений.
     */
    List<NotificationTask> findAllByNotificationDateTime(LocalDateTime notificationDateTime);

}
