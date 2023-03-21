package com.example.security.dto;

public class BookDto {
    private Long id;
    private String title;
    private String language;
    private String creationDate;
    private String description;

    // constructor, getters y setters


    public BookDto() {
    }

    public BookDto(Long id, String title, String language, String creationDate, String description) {
        this.id = id;
        this.title = title;
        this.language = language;
        this.creationDate = creationDate;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public BookDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public BookDto setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public BookDto setCreationDate(String creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BookDto setDescription(String description) {
        this.description = description;
        return this;
    }

}

