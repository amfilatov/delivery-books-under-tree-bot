package app.filatov.deliverybooksundertreebot.service;

import app.filatov.deliverybooksundertreebot.model.User;
import app.filatov.deliverybooksundertreebot.model.repository.UserRepository;
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
public class UserService {
    UserRepository repository;

    @Transactional
    public User getOrCreate(org.telegram.telegrambots.meta.api.objects.User user) {
        return repository.findById(user.getId()).orElseGet(() -> {
                    log.info("There is no user with id: {}, let's create it",
                            user.getId());

                    return repository.save(User.builder()
                            .id(user.getId())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .userName(user.getUserName())
                            .build());
                }
        );
    }

    @Transactional
    public User save(User user) {
        return repository.save(user);
    }
}
