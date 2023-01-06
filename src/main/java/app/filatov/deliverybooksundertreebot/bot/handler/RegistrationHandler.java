package app.filatov.deliverybooksundertreebot.bot.handler;


import app.filatov.deliverybooksundertreebot.model.EmailSettings;
import app.filatov.deliverybooksundertreebot.model.User;
import app.filatov.deliverybooksundertreebot.service.EmailSettingsService;
import app.filatov.deliverybooksundertreebot.service.MessageService;
import app.filatov.deliverybooksundertreebot.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationHandler implements MessageHandler {

    UserService userService;
    EmailSettingsService emailSettingsService;
    MessageService messageService;

    @Override
    public SendMessage handle(User user, Update update) {
        EmailSettings settings = emailSettingsService.getOrCreate(user);
        Message message = update.getMessage();

        // Отсутствуют корректные данные в сообщении
        if (message.getText() == null || message.getText().isBlank()) {
            return new SendMessage(String.valueOf(message.getChatId()),
                    messageService.getMessage("message.incorrect_data_entry"));
        }


        // Начинаем регистрацию, переводим статус, запрашиваем у пользователя адрес Send-to-Kindle
        if (user.getBotState() == User.BotState.NEW) {
            log.info("[{}] Starting user registration, user with id: {}, state: {}",
                    update.getUpdateId(),
                    user.getId(), user.getBotState().name());

            user.setBotState(User.BotState.SAVE_KINDLE_ACCOUNT);
            userService.save(user);

            return new SendMessage(String.valueOf(message.getChatId()),
                    messageService.getMessage("message.enter_kindle_email"));
        }

        // Сохраняем адрес Send-to-Kindle, запрашиваем адрес почты, с которой будет отправлено письмо
        if (user.getBotState() == User.BotState.SAVE_KINDLE_ACCOUNT) {
            log.info("[{}] Save kindle email with id: {}, state: {}",
                    update.getUpdateId(),
                    user.getId(), user.getBotState().name());

            settings.setKindleEmail(message.getText());
            try {
                emailSettingsService.save(settings);
            } catch (Exception e) {
                return new SendMessage(String.valueOf(message.getChatId()),
                        messageService.getMessage("message.invalid_email"));
            }

            user.setBotState(User.BotState.SAVE_EMAIL_LOGIN);
            userService.save(user);

            return new SendMessage(String.valueOf(message.getChatId()),
                    messageService.getMessage("message.enter_sender_email"));
        }

        // Сохраняем адрес почты, с которой будет отправлено письмо, запрашиваем пароль к нему
        if (user.getBotState() == User.BotState.SAVE_EMAIL_LOGIN) {
            log.info("[{}] Save sender email with id: {}, state: {}",
                    update.getUpdateId(),
                    user.getId(), user.getBotState().name());

            settings.setSenderEmail(message.getText());
            try {
                emailSettingsService.save(settings);
            } catch (Exception e) {
                return new SendMessage(String.valueOf(message.getChatId()),
                        messageService.getMessage("message.invalid_email"));
            }

            user.setBotState(User.BotState.SAVE_EMAIL_PASSWORD);
            userService.save(user);

            return new SendMessage(String.valueOf(message.getChatId()),
                    messageService.getMessage("message.enter_sender_password"));
        }

        // Сохраняем пароль, завершаем регистрацию
        log.info("[{}] Save sender password with id: {}, state: {}",
                update.getUpdateId(),
                user.getId(), user.getBotState().name());

        settings.setSenderPassword(message.getText());
        emailSettingsService.save(settings);

        user.setBotState(User.BotState.READY);
        userService.save(user);

        return new SendMessage(String.valueOf(message.getChatId()),
                messageService.getMessage("message.registration_completed"));
    }

    @Override
    public User.BotState getHandlerType() {
        return User.BotState.NEW;
    }
}
