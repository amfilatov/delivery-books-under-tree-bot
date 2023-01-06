package app.filatov.deliverybooksundertreebot.controller;

import app.filatov.deliverybooksundertreebot.bot.TelegramBot;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class WebHookController {
    TelegramBot telegramBot;

    @PostMapping
    public BotApiMethod<?> update(@RequestBody Update update) {
        log.info("[{}] Message received from user with id: {}",
                update.getUpdateId(),
                update.getMessage().getFrom().getId());

        return telegramBot.onWebhookUpdateReceived(update);
    }
}
