package app.filatov.deliverybooksundertreebot.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @Column(name = "id")
    Long id;

    @Column(name = "first_name", length = 1024)
    String firstName;

    @Column(name = "last_name", length = 1024)
    String lastName;

    @Column(name = "user_name", length = 1024, nullable = false)
    String userName;

    @Enumerated(EnumType.STRING)
    @Column(name = "bot_state", nullable = false)
    BotState botState;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Book> books = new ArrayList<>();

    @PrePersist
    void onCreate() {
        this.botState = BotState.NEW;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public enum BotState {
        NEW,
        SAVE_KINDLE_ACCOUNT,
        SAVE_EMAIL_LOGIN,
        SAVE_EMAIL_PASSWORD,
        READY
    }
}
