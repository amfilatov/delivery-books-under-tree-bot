package app.filatov.deliverybooksundertreebot.bot.handler;

import app.filatov.deliverybooksundertreebot.bot.TelegramBot;
import app.filatov.deliverybooksundertreebot.bot.config.TelegramBotProperties;
import app.filatov.deliverybooksundertreebot.model.Book;
import app.filatov.deliverybooksundertreebot.model.User;
import app.filatov.deliverybooksundertreebot.service.BookService;
import app.filatov.deliverybooksundertreebot.service.DownloadService;
import app.filatov.deliverybooksundertreebot.service.EmailService;
import app.filatov.deliverybooksundertreebot.service.MessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;


@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DocumentHandler implements MessageHandler {
    @Lazy
    TelegramBot bot;
    MessageService messageService;
    BookService bookService;
    @Lazy
    EmailService emailService;
    @Lazy
    DownloadService downloadService;
    TelegramBotProperties properties;

    @Override
    public SendMessage handle(User user, Update update) {
        Message message = update.getMessage();

        // Проверка на наличие вложения
        if (!message.hasDocument()) {
            log.info("[{}] Missing attachment", update.getUpdateId());
            return new SendMessage(String.valueOf(message.getChatId()),
                    messageService.getMessage("message.missing_attachment"));
        }

        // Проверка на совместимый формат вложения
        boolean hasCompatibleFormats = false;
        for (String format : properties.getCompatibleFormats()) {
            if (message.getDocument().getFileName().contains(format)) {
                hasCompatibleFormats = true;
                break;
            }
        }
        if (!hasCompatibleFormats) {
            log.info("[{}] Incompatible format", update.getUpdateId());
            return new SendMessage(String.valueOf(message.getChatId()),
                    messageService.getMessage("message.incompatible_formats"));
        }

        // Сохраняем информацию о вложении
        Book book = bookService.save(user, update);

        // Загружаем отправленный файл в память
        File bookFile = null;
        try {
            bookFile = downloadService.findFileById(book.getFileId());
        } catch (TelegramApiException e) {
            log.error("[{}] Failed to download file with id: {}",
                    message.getChatId(), book.getFileId());
            return new SendMessage(String.valueOf(message.getChatId()),
                    messageService.getMessage("message.failed_download_file"));
        }

        // Отправляем email с вложением
        try {
            bot.execute(new SendMessage(String.valueOf(message.getChatId()),
                    messageService.getMessage("message.being_send_file")));
            emailService.send(book, bookFile);
            book = bookService.setSendStatus(book);

            log.info("[{}] Book with id: {} sent successfully",
                    message.getChatId(), book.getFileId());
        } catch (Exception e) {
            log.error("[{}] Failed to send file with id: {}. {}",
                    message.getChatId(),
                    book.getFileId(), e.getMessage());
            return new SendMessage(String.valueOf(message.getChatId()),
                    messageService.getMessage("message.failed_send_file"));
        }

        return new SendMessage(String.valueOf(message.getChatId()),
                messageService.getMessage("message.sent_file"));
    }

    @Override
    public User.BotState getHandlerType() {
        return User.BotState.READY;
    }
}
