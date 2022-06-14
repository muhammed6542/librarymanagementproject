package com.hexad.librarymanagement.service;

import com.hexad.librarymanagement.dto.BookDto;
import com.hexad.librarymanagement.dto.BookManagementDto;
import com.hexad.librarymanagement.dto.UserDto;
import com.hexad.librarymanagement.entity.Book;
import com.hexad.librarymanagement.entity.BookManagement;
import com.hexad.librarymanagement.entity.User;
import com.hexad.librarymanagement.enums.BookStatus;
import com.hexad.librarymanagement.enums.UserBookStatus;
import com.hexad.librarymanagement.exception.NotFindBorrowedBookException;
import com.hexad.librarymanagement.repository.BookManagementRepository;
import com.hexad.librarymanagement.repository.BookRepository;
import com.hexad.librarymanagement.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookManagementServiceTest {

    @InjectMocks
    private BookManagementService bookManagementService;

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    @Mock
    private BookManagementRepository bookManagementRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    private Book book = createBook();
    private User user = createUser();
    private BookManagement bookManagement = createBookManagement(UserBookStatus.BORROW);

    @Before
    public void setUp() {
        bookService.addBook(new BookDto(1L,"deneme", "deneeme", 1, 1));
        userService.addUser(new UserDto(1L,"adsf"));

     }

    @Test
    public void testGetBookBoughtByTheUser() {

        ArrayList<BookManagement> bookManagementArrayList = new ArrayList<>();
        bookManagementArrayList.add(bookManagement);

        when(bookManagementRepository.findByBookIdAndUserBookStatus(1L, UserBookStatus.BORROW)).thenReturn(bookManagementArrayList);
        List<BookManagementDto> result = bookManagementService.getBookBoughtByTheUser(1L, UserBookStatus.BORROW);
        assertEquals(bookManagementArrayList.get(0).getBookId(), result.get(0).getBookId());
        verify(bookManagementRepository, times(1)).findByBookIdAndUserBookStatus(1L, UserBookStatus.BORROW);

    }

    @Test
    public void testGetUsersBook() {

        ArrayList<BookManagement> bookManagementArrayList = new ArrayList<>();
        bookManagementArrayList.add(bookManagement);

        when(bookManagementRepository.findByUserIdAndUserBookStatus(1L, UserBookStatus.BORROW)).thenReturn(bookManagementArrayList);
        List<BookManagementDto> result = bookManagementService.getUsersBook(1L, UserBookStatus.BORROW);
        assertEquals(bookManagementArrayList.get(0).getUserId(), result.get(0).getUserId());
        verify(bookManagementRepository, times(1)).findByUserIdAndUserBookStatus(1L, UserBookStatus.BORROW);

    }

    @Test
    public void testBorrowBookByUsers() {
        ArrayList<BookManagement> bookManagementArrayList = new ArrayList<>();
        bookManagementArrayList.add(bookManagement);

        when(bookManagementRepository.findByUserIdAndUserBookStatus(1L, UserBookStatus.BORROW)).thenReturn(bookManagementArrayList);

        bookManagementService.borrowBookByUsers(new BookManagementDto(1L,1L,BookStatus.ORIGINAL,UserBookStatus.BORROW));

    }

    @Test
    public void testViewBookByUsers() {
         bookManagementService.viewBookByUsers(new BookManagementDto(1L,1L,BookStatus.ORIGINAL,UserBookStatus.VIEW));
    }

    @Test
    public void testLendBackBookByUsersThrowNotFindBorrowedBookException() {
        BookManagement bookManagement = createBookManagement(UserBookStatus.LENDBACK);

        when(bookManagementRepository.findByUserIdAndBookIdAndUserBookStatusAndBookStatus(1L, 1L , UserBookStatus.LENDBACK,BookStatus.COPY)).thenReturn(bookManagement);

        assertThrows(NotFindBorrowedBookException.class, () -> {
            bookManagementService.lendBackBookByUsers(new BookManagementDto(1L,1L,BookStatus.ORIGINAL,UserBookStatus.BORROW));
        });

    }

    @Test
    public void testLendBackBookByUsers() {
        BookManagement bookManagement = createBookManagement(UserBookStatus.BORROW);

        when(bookManagementRepository.findByUserIdAndBookIdAndUserBookStatusAndBookStatus(1L, 1L , UserBookStatus.BORROW,BookStatus.ORIGINAL)).thenReturn(bookManagement);

        bookManagementService.lendBackBookByUsers(new BookManagementDto(1L,1L,BookStatus.ORIGINAL,UserBookStatus.BORROW));

    }


    private Book createBook() {
        Book book = new Book();
        book.setBookId(1L);
        book.setBookName("OTHER BOOK");
        book.setBookWriter("ONE WRITER");
        book.setBookStock(10);
        book.setCopyStock(20);
        book.setVersion(0L);
        return book;

    }


    private User createUser() {
        User user = new User();
        user.setUserId(1L);
        user.setUserName("OTHER BOOK");
        return user;

    }


    private BookManagement createBookManagement(UserBookStatus userBookStatus) {
        BookManagement bookManagement = new BookManagement();
        bookManagement.setUserId(1L);
        bookManagement.setBookId(1L);
        bookManagement.setBookStatus(BookStatus.ORIGINAL);
        bookManagement.setUserBookStatus(userBookStatus);
        return bookManagement;

    }
}
