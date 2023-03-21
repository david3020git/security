package com.example.security.repository;

import com.example.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long > {

    // si quiero iniciar sesion con el user name debo cambia y buscarusername en ves de email
    Optional<User> findByEmail(String email);

    boolean existsById(Long id);

    void deleteById(Long id);


}
