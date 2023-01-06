package app.filatov.deliverybooksundertreebot.bot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "locale")
public class LocaleProperties {
    private String tag;
}
