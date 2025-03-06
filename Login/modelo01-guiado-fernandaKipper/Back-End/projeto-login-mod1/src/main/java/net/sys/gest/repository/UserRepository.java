package net.sys.gest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.sys.gest.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User>findByEmail(String email);
}
