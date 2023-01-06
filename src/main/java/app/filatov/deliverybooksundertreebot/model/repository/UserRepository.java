package app.filatov.deliverybooksundertreebot.model.repository;

import app.filatov.deliverybooksundertreebot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}