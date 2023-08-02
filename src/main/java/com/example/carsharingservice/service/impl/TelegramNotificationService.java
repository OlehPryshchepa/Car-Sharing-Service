package com.example.carsharingservice.service.impl;

import java.util.Map;
import com.example.carsharingservice.bot.TelegramBot;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.service.NotificationService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class TelegramNotificationService implements NotificationService {
    private final TelegramBot telegramBot;

    public TelegramNotificationService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void sendNewRentalNotification(Rental rental) {
        String message = "New rental created: Rental ID - " + rental.getId()
                + ", User - " + rental.getUser().getFirstName()
                + ", Car - " + rental.getCar().getModel();
        sendTelegramMessage(rental.getUser().getId(), message);
    }

    @Override
    public void sendOverdueRentalNotification(Rental rental) {
        String message = "Overdue rental: Rental ID - " + rental.getId()
                + ", User - " + rental.getUser().getFirstName()
                + ", Car - " + rental.getCar().getModel();
        sendTelegramMessage(rental.getUser().getId(), message);
    }

    @Override
    public void sendPaymentSuccessNotification(Payment payment) {
        String message = "Payment successful for Order ID - " + payment.getId()
                + ", User - " + payment.getRental().getUser().getFirstName()
                + ", Amount - " + payment.getPaymentAmount();
        sendTelegramMessage(payment.getRental().getUser().getId(), message);
    }

    private void sendTelegramMessage(Long userId, String message) {
        Map<Long, Long> userChatId = telegramBot.userChatId;
        Long chatId = userChatId.get(userId);
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .build();
        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
