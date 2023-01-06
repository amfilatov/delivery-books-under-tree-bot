package app.filatov.deliverybooksundertreebot.service;

import app.filatov.deliverybooksundertreebot.exception.NotFoundException;
import app.filatov.deliverybooksundertreebot.model.EmailSettings;
import app.filatov.deliverybooksundertreebot.model.User;
import app.filatov.deliverybooksundertreebot.model.repository.EmailSettingsRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailSettingsService {
    EmailSettingsRepository repository;

    @Transactional
    public EmailSettings getOrCreate(User user) {
        return repository.findById(user.getId()).orElseGet(() -> {
            log.info("There is no email settings with id: {}, let's create them",
                    user.getId());

            return repository.save(EmailSettings.builder()
                    .id(user.getId())
                    .user(user)
                    .build());
        });
    }

    public EmailSettings findByUserId(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("User with id: %d not found", id)));
    }

    @Transactional
    public EmailSettings save(EmailSettings emailSettings) {
        return repository.save(emailSettings);
    }
}
