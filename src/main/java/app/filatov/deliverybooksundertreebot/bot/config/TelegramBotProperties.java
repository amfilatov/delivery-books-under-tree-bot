package app.filatov.deliverybooksundertreebot.bot.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "telegram")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramBotProperties {
    String botUsername;
    String botToken;
    String botPath;
    List<String> compatibleFormats;
}
