package com.hexad.librarymanagement.service;

import com.hexad.librarymanagement.dto.BookDto;
import com.hexad.librarymanagement.entity.Book;
import com.hexad.librarymanagement.exception.BookNotFoundException;
import com.hexad.librarymanagement.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {


    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book bookOne;
    private Book bookTwo;
    private BookDto bookDtoOne;
    private BookDto bookDtoTwo;
    private List<Book> bookList;
    private List<BookDto> bookDtoList;

    @BeforeEach
    public void setUp() {
        bookList = new ArrayList<>();
        bookDtoList = new ArrayList<>();

        bookOne = createBook(1L);
        bookDtoOne = new BookDto(bookOne);

        bookTwo = createBook(2L);
        bookDtoTwo = new BookDto(bookTwo);

        bookList.add(bookOne);
        bookList.add(bookTwo);

        bookDtoList.add(bookDtoOne);
        bookDtoList.add(bookDtoTwo);
    }

    @AfterEach
    public void tearDown() {
        bookOne = bookTwo = null;
        bookList = null;
    }

    @Test
    public void testAddbook() {
        when(bookRepository.save(any())).thenReturn(bookOne);
        bookService.addBook(bookDtoOne);
        verify(bookRepository, times(1)).save(any());
    }



    @Test
    public void testExistById() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        assertThat(bookService.bookExist(bookOne.getBookId())).isEqualTo(true);
    }

    @Test
    public void testNotExistById() {
        when(bookRepository.existsById(1L)).thenReturn(false);
        assertThat(bookService.bookExist(bookOne.getBookId())).isEqualTo(false);
    }


    @Test
    public void testDeletebookThrowException() {
        when(bookRepository.existsById(1L)).thenReturn(false);
        assertThrows(BookNotFoundException.class, () -> {
            bookService.deleteBook(1L);
        });
    }

    private Book createBook(Long id) {
        Book book = new Book();
        book.setBookId(id);
        book.setBookName("OTHER BOOK");
        book.setBookWriter("ONE WRITER");
        book.setBookStock(10);
        book.setCopyStock(20);
        book.setVersion(0L);
        return book;

    }

}
