package app.filatov.deliverybooksundertreebot.bot.router;

import app.filatov.deliverybooksundertreebot.model.User;
import app.filatov.deliverybooksundertreebot.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageRouter {
    UserService userService;
    StateRouter stateRouter;

    public SendMessage handleUpdate(Update update) {
        User user = userService.getOrCreate(update.getMessage().getFrom());
        log.info("[{}] User with id: {} has state: {}",
                update.getUpdateId(),
                user.getId(), user.getBotState());

        return stateRouter.processTextMessage(user, update);
    }
}
