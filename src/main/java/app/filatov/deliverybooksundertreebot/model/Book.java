package app.filatov.deliverybooksundertreebot.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@Table(name = "books")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_settings_gen")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "file_unique_id", nullable = false)
    String fileUniqueId;

    @Column(name = "file_id", nullable = false)
    String fileId;

    @Column(name = "file_name", nullable = false)
    String fileName;

    @Column(name = "mimi_type", nullable = false)
    String mimeType;

    @Column(name = "file_size", nullable = false)
    Long fileSize;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "created", nullable = false)
    LocalDateTime created;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    State state;

    @PrePersist
    void onCreate() {
        state = State.NEW;
        created = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return fileId.equals(book.fileId);
    }

    @Override
    public int hashCode() {
        return fileId.hashCode();
    }

    public enum State {
        NEW,
        SEND
    }
}
