package com.hexad.librarymanagement.service;

import com.hexad.librarymanagement.dto.BookDto;
import com.hexad.librarymanagement.dto.BookManagementDto;
import com.hexad.librarymanagement.entity.Book;
import com.hexad.librarymanagement.enums.BookStatus;
import com.hexad.librarymanagement.exception.BookNotFoundException;
import com.hexad.librarymanagement.exception.ConcurrentStockUpdateException;
import com.hexad.librarymanagement.exception.StockNotFoundException;
import com.hexad.librarymanagement.repository.BookRepository;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookService {
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(BookDto bookDto) {
        bookRepository.save(new Book(bookDto));
    }

    public BookDto getBook(Long bookId) {
        Book book = bookRepository.findByBookId(bookId);
        return new BookDto(book);
    }

    @Transactional
    public void deleteBook(Long bookId) {
        if (bookExist(bookId))
            bookRepository.deleteByBookId(bookId);
        else throw new BookNotFoundException();
    }

    public void updateBook(Long bookId, BookDto bookDto) {
        if (bookExist(bookId)) {
            Book book = new Book(bookDto);
            book.setBookId(bookId);
            bookRepository.save(book);
        }
        else throw new BookNotFoundException();
    }

    public List<BookDto> getAllBook() {

        return bookRepository
                .findAll()
                .stream()
                .map(BookDto::new)
                .collect(Collectors.toList());
    }


    public boolean bookExist(Long bookId) {
        return bookRepository.existsById(bookId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateStock(Long bookId,  Integer stockCount) {

        try {
            Book book = bookRepository.findByBookId(bookId);
            if (Objects.isNull(book)) {
                throw new BookNotFoundException();
            }
            if (book.getBookStock() == 0) {
                throw new StockNotFoundException();
            }
            book.setBookStock(book.getBookStock() - stockCount);
            bookRepository.save(book);
        } catch (
                OptimisticLockingFailureException e) {
            throw new ConcurrentStockUpdateException();
        }
    }
}
