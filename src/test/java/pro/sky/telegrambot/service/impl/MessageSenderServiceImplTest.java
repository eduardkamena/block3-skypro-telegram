package pro.sky.telegrambot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageSenderServiceImplTest {

    @Mock
    private TelegramBot telegramBot;

    @InjectMocks
    private MessageSenderServiceImpl messageSenderService;

    @Test
    void shouldSendMessageSuccessfully() {
        // given
        Long chatId = 123L;
        String messageText = "Test message";
        SendResponse sendResponse = mock(SendResponse.class);

        // when
        when(sendResponse.isOk()).thenReturn(true);
        when(telegramBot.execute(any(SendMessage.class))).thenReturn(sendResponse);
        messageSenderService.sendMessage(chatId, messageText);

        // then
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    void shouldHandleFailedSend() {
        // given
        Long chatId = 123L;
        String messageText = "Test message";
        SendResponse sendResponse = mock(SendResponse.class);

        // when
        when(sendResponse.isOk()).thenReturn(false);
        when(telegramBot.execute(any(SendMessage.class))).thenReturn(sendResponse);
        messageSenderService.sendMessage(chatId, messageText);

        // then
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }

}
