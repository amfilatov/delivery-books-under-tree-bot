package app.filatov.deliverybooksundertreebot.bot.config;

import app.filatov.deliverybooksundertreebot.bot.TelegramBot;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TelegramBotConfig {
    TelegramBotProperties properties;

    @Bean
    public TelegramBot telegramBot() {
        return TelegramBot.builder()
                .botUsername(properties.getBotUsername())
                .botToken(properties.getBotToken())
                .botPath(properties.getBotPath())
                .build();
    }
}
