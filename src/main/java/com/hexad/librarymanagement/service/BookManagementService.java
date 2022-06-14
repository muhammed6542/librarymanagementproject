package com.hexad.librarymanagement.service;

import com.hexad.librarymanagement.dto.BookManagementDto;
import com.hexad.librarymanagement.entity.BookManagement;
import com.hexad.librarymanagement.enums.BookStatus;
import com.hexad.librarymanagement.enums.UserBookStatus;
import com.hexad.librarymanagement.exception.LimitReachedException;
import com.hexad.librarymanagement.exception.NotFindBorrowedBookException;
import com.hexad.librarymanagement.repository.BookManagementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookManagementService {
    private BookManagementRepository bookManagementRepository;
    private BookService bookService;

    public BookManagementService(BookManagementRepository bookManagementRepository, BookService bookService) {
        this.bookManagementRepository = bookManagementRepository;
        this.bookService = bookService;
    }

    public List<BookManagementDto> getBookBoughtByTheUser(Long bookId, UserBookStatus userBookStatus) {

        return bookManagementRepository.findByBookIdAndUserBookStatus(bookId, userBookStatus)
                .stream()
                .map(BookManagementDto::new)
                .collect(Collectors.toList());
    }

    public List<BookManagementDto> getUsersBook(Long userId, UserBookStatus userBookStatus) {

        return bookManagementRepository.findByUserIdAndUserBookStatus(userId, userBookStatus)
                .stream()
                .map(BookManagementDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void borrowBookByUsers(BookManagementDto bookManagementDto) {
        List<BookManagement> bookManagement = bookManagementRepository.findByUserIdAndUserBookStatus(bookManagementDto.getUserId(), UserBookStatus.BORROW);
        if (!CollectionUtils.isEmpty(bookManagement)) {
            long bookCount = bookManagement.stream().filter(bookManage -> bookManage.getBookStatus() == BookStatus.ORIGINAL).count();
            if (bookCount == 2) {
                throw new LimitReachedException();
            }
            bookCount = bookManagement.stream().filter(bookManage -> bookManage.getBookStatus() == BookStatus.COPY).count();
            if (bookCount == 1) {
                throw new LimitReachedException();
            }
        }
        bookManagementDto.setUserBookStatus(UserBookStatus.BORROW);
        bookManagemenetService(bookManagementDto);
    }

    @Transactional
    public void viewBookByUsers(BookManagementDto bookManagementDto) {

        bookManagementDto.setUserBookStatus(UserBookStatus.VIEW);
        bookManagemenetService(bookManagementDto);

    }

    @Transactional
    private void bookManagemenetService(BookManagementDto bookManagementDto) {

        updateStock(bookManagementDto, 1);
        BookManagement bookManagement = new BookManagement();
        bookManagement.setBookId(bookManagementDto.getBookId());
        bookManagement.setUserId(bookManagementDto.getUserId());
        bookManagement.setBookStatus(bookManagementDto.getBookStatus());
        bookManagement.setUserBookStatus(bookManagementDto.getUserBookStatus());
        bookManagementRepository.save(bookManagement);
    }

    public void lendBackBookByUsers(BookManagementDto bookManagementDto) {
        BookManagement bookManagement = bookManagementRepository.findByUserIdAndBookIdAndUserBookStatusAndBookStatus(bookManagementDto.getUserId(), bookManagementDto.getBookId(), bookManagementDto.getUserBookStatus(), bookManagementDto.getBookStatus());
        if (Objects.isNull(bookManagement)) {
            throw new NotFindBorrowedBookException();
        }
        updateStock(bookManagementDto, -1);
        bookManagement.setUserBookStatus(UserBookStatus.LENDBACK);
        bookManagementRepository.save(bookManagement);

    }

    @Transactional
    public void updateStock(BookManagementDto bookManagementDto, Integer stockCount) {
        bookService.updateStock(bookManagementDto.getBookId(), stockCount);
    }
}
