package app.filatov.deliverybooksundertreebot.service;

import app.filatov.deliverybooksundertreebot.model.Book;
import app.filatov.deliverybooksundertreebot.model.User;
import app.filatov.deliverybooksundertreebot.model.repository.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookService {
    BookRepository repository;

    @Transactional
    public Book save(User user, Update update) {
        Document document = update.getMessage().getDocument();
        return repository.save(Book.builder()
                .fileId(document.getFileId())
                .fileUniqueId(document.getFileUniqueId())
                .fileName(document.getFileName())
                .mimeType(document.getMimeType())
                .fileSize(document.getFileSize())
                .user(user)
                .build());
    }

    @Transactional
    public Book setSendStatus(Book book) {
        book.setState(Book.State.SEND);
        return repository.save(book);
    }
}
