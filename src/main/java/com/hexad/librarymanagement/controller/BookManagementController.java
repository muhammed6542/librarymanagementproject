package com.hexad.librarymanagement.controller;

import com.hexad.librarymanagement.dto.BookManagementDto;
import com.hexad.librarymanagement.enums.UserBookStatus;
import com.hexad.librarymanagement.exception.*;
import com.hexad.librarymanagement.service.BookManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/bookManagement")
public class BookManagementController {
    private BookManagementService bookManagementService;

    public BookManagementController(BookManagementService bookManagementService) {
        this.bookManagementService = bookManagementService;
    }

    @PostMapping("/borrowBookByUser")
    public ResponseEntity<String> borrowBookByUsers(@RequestBody BookManagementDto bookManagementDto) {
        try {
            bookManagementService.borrowBookByUsers(bookManagementDto);
            return ResponseEntity.status(OK).body("User borrowed book ");

        } catch (BookNotFoundException | StockNotFoundException | UserNotFoundException | LimitReachedException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (ConcurrentStockUpdateException e) {
            return ResponseEntity.status(FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("/viewBookByUsers")
    public ResponseEntity<String> viewBookByUsers(@RequestBody BookManagementDto bookManagementDto) {
        try {
            bookManagementService.viewBookByUsers(bookManagementDto);
            return ResponseEntity.status(OK).body("User viewed book ");

        } catch (BookNotFoundException | StockNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (ConcurrentStockUpdateException e) {
            return ResponseEntity.status(FORBIDDEN).body(e.getMessage());
        }
    }


    @PostMapping("/lendBackBookByUsers")
    public ResponseEntity<String> lendBackBookByUsers(@RequestBody BookManagementDto bookManagementDto) {
        try {
            bookManagementService.lendBackBookByUsers(bookManagementDto);
            return ResponseEntity.status(OK).body("User lent back book ");

        } catch (BookNotFoundException | StockNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (ConcurrentStockUpdateException e) {
            return ResponseEntity.status(FORBIDDEN).body(e.getMessage());
        }
    }


    @GetMapping("/getUsersBook")
    public ResponseEntity<List<BookManagementDto>> getUsersBook(@RequestParam Long userId, @RequestParam UserBookStatus userBookStatus) {
        List<BookManagementDto> bookManagement = bookManagementService.getUsersBook(userId, userBookStatus);
        return new ResponseEntity<>(bookManagement, OK);
    }

    @GetMapping("/getBookBoughtByTheUser")
    public ResponseEntity<List<BookManagementDto>> getBookBoughtByTheUser(@RequestParam Long bookId, @RequestParam UserBookStatus userBookStatus) {
        List<BookManagementDto> bookManagement = bookManagementService.getBookBoughtByTheUser(bookId, userBookStatus);
        return new ResponseEntity<>(bookManagement, OK);
    }


}
