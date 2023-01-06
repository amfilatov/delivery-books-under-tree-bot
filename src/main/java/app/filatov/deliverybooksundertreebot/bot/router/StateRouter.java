package app.filatov.deliverybooksundertreebot.bot.router;

import app.filatov.deliverybooksundertreebot.bot.handler.MessageHandler;
import app.filatov.deliverybooksundertreebot.model.User;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StateRouter {
    Map<User.BotState, MessageHandler> handlers = new HashMap<>();

    public StateRouter(List<MessageHandler> handlers) {
        handlers.forEach(handler -> this.handlers.put(handler.getHandlerType(), handler));
    }

    // Обработка сообщений содержащих текст
    public SendMessage processTextMessage(User user, Update update) {
        User.BotState handler = getHandlerByState(user.getBotState());
        log.info("[{}] For user with id: {}, handler {} is used",
                update.getUpdateId(),
                user.getId(), handler.name());
        MessageHandler messageHandler = handlers.get(handler);

        return messageHandler.handle(user, update);
    }

    private User.BotState getHandlerByState(User.BotState state) {
        return switch (state) {
            case NEW,
                    SAVE_KINDLE_ACCOUNT,
                    SAVE_EMAIL_LOGIN,
                    SAVE_EMAIL_PASSWORD -> User.BotState.NEW;
            case READY -> User.BotState.READY;
        };
    }
}
