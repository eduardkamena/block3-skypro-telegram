package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import pro.sky.telegrambot.service.MessageSenderService;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TelegramBotUpdatesListenerTest {

    @Mock
    private MessageSenderService messageSenderService;

    @Mock
    private NotificationTaskRepository notificationTaskRepository;

    @InjectMocks
    private TelegramBotUpdatesListener telegramBotUpdatesListener;

    private final String WELCOME_MESSAGE =
            "Добро пожаловать в бот, отправляющий Вам напоминания!\n\n"
                    + "Введите /info";
    private final String EXAMPLE_MESSAGE =
            "Введите задачу в формате <01.01.2022 20:00 Сделать домашнюю работу>";

    @Test
    void shouldHandleStartCommand() {
        // given
        Update update = createUpdateWithText("/start");

        // when
        when(update.message().chat().id()).thenReturn(123L);
        telegramBotUpdatesListener.process(List.of(update));

        // then
        verify(messageSenderService, times(1))
                .sendMessage(123L, WELCOME_MESSAGE);
    }

    @Test
    void shouldHandleInfoCommand() {
        // given
        Update update = createUpdateWithText("/info");

        // when
        when(update.message().chat().id()).thenReturn(123L);
        telegramBotUpdatesListener.process(List.of(update));

        // then
        verify(messageSenderService, times(1))
                .sendMessage(123L, EXAMPLE_MESSAGE);
    }

    @Test
    void shouldHandleValidNotificationMessage() {
        // given
        String validMessage = "01.01.2023 20:00 Test reminder";
        Update update = createUpdateWithText(validMessage);

        // when
        when(update.message().chat().id()).thenReturn(123L);
        telegramBotUpdatesListener.process(List.of(update));

        // then
        verify(notificationTaskRepository, times(1))
                .save(any());
        verify(messageSenderService, times(1))
                .sendMessage(eq(123L),
                        contains("Напоминание успешно сохранено!"));
    }

    @Test
    void shouldHandleInvalidNotificationMessage() {
        // given
        String invalidMessage = "Invalid message format";
        Update update = createUpdateWithText(invalidMessage);

        // when
        when(update.message().chat().id()).thenReturn(123L);
        telegramBotUpdatesListener.process(List.of(update));

        // then
        verify(notificationTaskRepository, never()).save(any());
        verify(messageSenderService, never()).sendMessage(eq(123L),
                contains("Напоминание успешно сохранено!"));
    }

    private Update createUpdateWithText(String text) {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(text);
        when(message.chat()).thenReturn(mock(com.pengrad.telegrambot.model.Chat.class));
        when(message.chat().id()).thenReturn(123L);
        return update;
    }

}
