package com.example.carsharingservice.bot;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;
    private final UserService userService;
    public Map<Long, Long> userChatId = new HashMap<>();

    public TelegramBot(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(@NotNull Update update) {
        Long chatId = update.getMessage().getFrom().getId();
        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            SendMessage message = SendMessage.builder()
                    .chatId(chatId)
                    .text("Welcome to our CarSharingService bot!" + "\n" +
                    "You'll receive any significant notifications about our cooperation here. " +
                            "To begin, please, enter your email:")
                    .build();
            try {
                sendApiMethod(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException("Can't send a message",e);
            }
        } else if (update.hasMessage()) {
            String userText = update.getMessage().getText();
            if (update.getMessage().getText().contains("@")) {
                Optional<User> userOptional = userService.findByEmail(userText);
                SendMessage message = null;
                if (userOptional.isPresent() && userOptional.get().getRole().equals(User.Role.MANAGER)) {
                    User user = userOptional.get();
                    userChatId.put(user.getId(), chatId);
                    message = SendMessage.builder()
                            .chatId(chatId)
                            .text("Hello " + user.getFirstName() + "!" + "\n"
                                    + "We can send you notifications now :) Stay tuned! ;)")
                            .build();
                } else {
                    message = SendMessage.builder()
                            .chatId(chatId)
                            .text("Email is invalid.")
                            .build();
                }
                try {
                    sendApiMethod(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException("Can't send a message", e);
                }
            } else {
                SendMessage message = SendMessage.builder()
                        .chatId(chatId)
                        .text("Please, enter correct email.")
                        .build();
                try {
                    sendApiMethod(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException("Can't send a message", e);
                }
            }
        }
    }
}
