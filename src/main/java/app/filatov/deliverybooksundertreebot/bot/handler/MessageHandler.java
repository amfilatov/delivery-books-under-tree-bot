package app.filatov.deliverybooksundertreebot.bot.handler;

import app.filatov.deliverybooksundertreebot.model.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageHandler {
    SendMessage handle(User user, Update update);

    User.BotState getHandlerType();
}
