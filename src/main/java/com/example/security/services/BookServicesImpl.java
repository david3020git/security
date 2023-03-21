package com.example.security.services;

import com.example.security.repository.BookRepository;
import com.example.security.user.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServicesImpl implements BookServices {


    private BookRepository bookRepository;

    private final Logger log = LoggerFactory.getLogger(BookServicesImpl.class);

    public BookServicesImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        if (bookRepository == null) {
            log.info("BookRepository is null");
        }
        else {
            log.info("BookRepository not is null :) ");
        }
        return this.bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return this.bookRepository.findById(id);
    }

    @Override
    public Long count() {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting book by id");
        if (id == null || id < 0 || id == 0) {
            log.warn("Trying to delete book with wrong id");
            return;
        }

        try {
            this.bookRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error trying to delete book by id {}", id, e);
        }

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Book> findByName(String name) {
        return null;
    }

    @Override
    public Book save(Book book) {
        Book bookDb = this.bookRepository.save(book);

        return bookDb;
    }
}
