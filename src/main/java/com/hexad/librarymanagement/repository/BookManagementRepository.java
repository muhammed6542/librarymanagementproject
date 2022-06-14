package com.hexad.librarymanagement.repository;

import com.hexad.librarymanagement.entity.BookManagement;
import com.hexad.librarymanagement.enums.BookStatus;
import com.hexad.librarymanagement.enums.UserBookStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookManagementRepository extends CrudRepository<BookManagement, Long> {

        List<BookManagement> findByBookIdAndUserBookStatus(Long bookId, UserBookStatus userBookStatus);
        List<BookManagement> findByUserIdAndUserBookStatus(Long userId, UserBookStatus userBookStatus);

        BookManagement findByUserIdAndBookIdAndUserBookStatusAndBookStatus(Long userId, Long bookId, UserBookStatus userBookStatus, BookStatus bookStatus);

}
