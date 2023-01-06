package app.filatov.deliverybooksundertreebot.model.repository;

import app.filatov.deliverybooksundertreebot.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}