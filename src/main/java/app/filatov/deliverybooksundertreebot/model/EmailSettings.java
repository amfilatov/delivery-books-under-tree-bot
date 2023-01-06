package app.filatov.deliverybooksundertreebot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Builder
@Table(name = "email_settings")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_settings_gen")
    @SequenceGenerator(name = "email_settings_gen", sequenceName = "email_settings_seq")
    @Column(name = "id", nullable = false)
    Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    User user;
    @Email
    @Column(name = "kindle_email", length = 1024)
    String kindleEmail;

    @Email
    @Column(name = "sender_email", length = 1024)
    String senderEmail;

    @Column(name = "sender_password", length = 1024)
    String senderPassword;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmailSettings that = (EmailSettings) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
