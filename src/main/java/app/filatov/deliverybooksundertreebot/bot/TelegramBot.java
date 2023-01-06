package app.filatov.deliverybooksundertreebot.bot;

import app.filatov.deliverybooksundertreebot.bot.router.MessageRouter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TelegramBot extends TelegramWebhookBot {
    @Autowired
    MessageRouter router;
    @NonFinal
    String botToken;
    @NonFinal
    String botPath;
    @NonFinal
    String botUsername;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return router.handleUpdate(update);
    }

    @Override
    public String getBotPath() {
        return botPath;
    }
}
