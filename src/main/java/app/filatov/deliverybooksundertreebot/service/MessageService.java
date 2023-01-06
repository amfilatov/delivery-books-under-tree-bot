package app.filatov.deliverybooksundertreebot.service;

import app.filatov.deliverybooksundertreebot.bot.config.LocaleProperties;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageService {
    MessageSource messageSource;
    Locale locale;

    public MessageService(MessageSource messageSource, LocaleProperties localeProperties) {
        this.messageSource = messageSource;
        this.locale = Locale.forLanguageTag(localeProperties.getTag());
    }

    public String getMessage(String message) {
        return messageSource.getMessage(message, null, locale);
    }
}