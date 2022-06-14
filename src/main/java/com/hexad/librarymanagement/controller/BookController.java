package com.hexad.librarymanagement.controller;

import com.hexad.librarymanagement.dto.BookDto;
import com.hexad.librarymanagement.entity.Book;
import com.hexad.librarymanagement.exception.BookNotFoundException;
import com.hexad.librarymanagement.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/book")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody BookDto book) {
        bookService.addBook(book);
        return ResponseEntity.status(OK).body("Book added");

    }

    @PostMapping("/update/{bookId}")
    public ResponseEntity<String> updateBook(@PathVariable Long bookId, @RequestBody BookDto book) {
        try {
            bookService.updateBook(bookId, book);
            return ResponseEntity.status(OK).body("Book updated");
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get/{bookId}")
    public ResponseEntity<BookDto> getBook(@PathVariable Long bookId) {

        BookDto book = bookService.getBook(bookId);
        return new ResponseEntity<>(book, OK);

    }

    @GetMapping("/getAll")
    public ResponseEntity<List<BookDto>> getAllBook() {

        List<BookDto> book = bookService.getAllBook();
        return new ResponseEntity<>(book, OK);

    }


    @DeleteMapping("{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable Long bookId) {
        try {
            bookService.deleteBook(bookId);
            return ResponseEntity.status(OK).body("Book removed");
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }
}
