package app.filatov.deliverybooksundertreebot;

import app.filatov.deliverybooksundertreebot.bot.config.LocaleProperties;
import app.filatov.deliverybooksundertreebot.bot.config.TelegramBotProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({TelegramBotProperties.class, LocaleProperties.class})
public class DeliveryBooksUnderTreeBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeliveryBooksUnderTreeBotApplication.class, args);
    }
}
