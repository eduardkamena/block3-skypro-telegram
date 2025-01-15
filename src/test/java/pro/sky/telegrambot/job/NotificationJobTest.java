package pro.sky.telegrambot.job;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import pro.sky.telegrambot.service.MessageSenderService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationJobTest {

    @Mock
    private NotificationTaskRepository notificationTaskRepository;

    @Mock
    private MessageSenderService messageSenderService;

    @InjectMocks
    private NotificationJob notificationJob;

    @Test
    void shouldSendAndDeleteNotificationTask() {
        // given
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        NotificationTask task = new NotificationTask(1L, 123L, "Test message", now);

        // when
        when(notificationTaskRepository.findAllByNotificationDateTime(now))
                .thenReturn(List.of(task));
        notificationJob.sendNotification();

        // then
        verify(messageSenderService, times(1))
                .sendMessage(task.getChatId(), "Напоминание! - " + task.getMessageText());
        verify(notificationTaskRepository, times(1)).delete(task);
    }

    @Test
    void shouldSendAndDeleteEmptyNotificationTask() {
        // given
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        // when
        when(notificationTaskRepository.findAllByNotificationDateTime(now))
                .thenReturn(List.of());
        notificationJob.sendNotification();

        // then
        verify(messageSenderService, never()).sendMessage(anyLong(), anyString());
        verify(notificationTaskRepository, never()).delete(any());
    }

}
