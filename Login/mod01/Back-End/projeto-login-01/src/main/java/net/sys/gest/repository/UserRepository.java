package net.sys.gest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.sys.gest.domain.User;

public interface UserRepository  extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
}
