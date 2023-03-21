package com.example.security.services;

import com.example.security.user.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookServices {

    List<Book> findAll();
    Optional<Book> findById(Long id);

    Long count();

    void deleteById(Long id);

    void deleteAll();

    List<Book> findByName(String name);

    Book save(Book book);

}
