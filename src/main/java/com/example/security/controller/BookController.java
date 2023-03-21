package com.example.security.controller;

import com.example.security.dto.BookDto;
import com.example.security.exception.BookNotFoundException;
import com.example.security.services.BookServices;
import com.example.security.user.Book;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("https://react-tailwind-h1w547pww-david3020git.vercel.app")
//@CrossOrigin(origins = {"https://react-tailwind-liart.vercel.app",})
@RequestMapping("/api/v1/demo-books")
@RequiredArgsConstructor
public class BookController {


    private final BookServices bookServices;

    private final Logger log = LoggerFactory.getLogger(BookController.class);

    //Ver todos los libros

//    @GetMapping("/view-all")
//    public List<Book> findAllBooks() {
//
//        return this.bookServices.findAll();
//    }

    @GetMapping("/view-all")
    public List<BookDto> findAllBooks() {
        List<Book> books = this.bookServices.findAll();
        List<BookDto> bookDtos = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        for (Book book : books) {
            String formattedDate = formatter.format(book.getCreationDate());
            BookDto bookDto = new BookDto();
            bookDto.setId(book.getId());
            bookDto.setTitle(book.getTitle());
            bookDto.setLanguage(book.getLanguage());
            bookDto.setCreationDate(formattedDate);
            bookDto.setDescription(book.getDescription());
            bookDtos.add(bookDto);
        }

        return bookDtos;
    }

    @PostMapping("/create-book")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book){
        if(book.getId() != null){
            log.warn("Trying to create a new car with existent id");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.bookServices.save(book));
    }

    @PutMapping("/update-book/{id}")
    @ApiOperation("buscar un ordenador por clave primaria id long")
    Book updateBook(@RequestBody Book updatebook, @PathVariable Long id){
        return bookServices.findById(id)
                .map(book -> {

                    book.setTitle(updatebook.getTitle());
                    book.setLanguage(updatebook.getLanguage());
                    book.setCreationDate(updatebook.getCreationDate());
                    book.setDescription(updatebook.getDescription());

                    return bookServices.save(book);
                }).orElseThrow(() -> new BookNotFoundException(id));

    }

//    @GetMapping("/view-detail/{id}")
//    Book viewDetail(@PathVariable Long id){
//        return bookServices.findById(id)
//                .orElseThrow(()->new BookNotFoundException(id));
//    }

    @GetMapping("/view-detail/{id}")
    public BookDto viewDetail(@PathVariable Long id) {
        Book book = bookServices.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(book.getCreationDate());

        return new BookDto(book.getId(), book.getTitle(), book.getLanguage(), formattedDate, book.getDescription());
    }


    @DeleteMapping("/delete-book/{id}")
    String deleteBook(@PathVariable Long id){
        bookServices.deleteById(id);

        return "User with id " + id + "has been deleted success";
    }
}
