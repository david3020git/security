package com.example.security.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name=("books"))
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
     private Long id;

    @NotBlank(message = "The title cannot be empty")
    private String title;

    @NotBlank(message = "The language cannot be empty")
    private String language;


    private Date creationDate;
    @NotBlank(message = "The description cannot be empty")
    private String description;


    public Long getId() {
        return id;
    }

    public Book setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public Book setLanguage(String language) {
        this.language = language;
        return this;
    }


    public Date getCreationDate() {
        return creationDate;
    }

    public Book setCreationDate(Date creationDate) {

        this.creationDate = creationDate;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Book setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", language='" + language + '\'' +
                ", creationDate=" + creationDate +
                ", description='" + description + '\'' +
                '}';
    }
}
