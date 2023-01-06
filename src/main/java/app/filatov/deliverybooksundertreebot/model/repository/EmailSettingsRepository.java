package app.filatov.deliverybooksundertreebot.model.repository;

import app.filatov.deliverybooksundertreebot.model.EmailSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailSettingsRepository extends JpaRepository<EmailSettings, Long> {
}