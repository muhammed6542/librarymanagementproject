package com.hexad.librarymanagement.dto;

import com.hexad.librarymanagement.entity.BookManagement;
import com.hexad.librarymanagement.enums.BookStatus;
import com.hexad.librarymanagement.enums.UserBookStatus;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookManagementDto {

    private @NotNull Long userId;
    private @NotNull Long bookId;
    private @NotNull BookStatus bookStatus;
    private @NotNull UserBookStatus userBookStatus;

    public BookManagementDto(@NotNull Long userId,@NotNull  Long bookId,@NotNull  BookStatus bookStatus, @NotNull UserBookStatus userBookStatus) {
        this.userId = userId;
        this.bookId = bookId;
        this.bookStatus = bookStatus;
        this.userBookStatus = userBookStatus;
    }

    public BookManagementDto(){

    }

    public BookManagementDto(BookManagement bookManagement){
        this.setUserId(bookManagement.getUserId());
        this.setBookId(bookManagement.getBookId());
        this.setBookStatus(bookManagement.getBookStatus());
        this.setUserBookStatus(bookManagement.getUserBookStatus());
    }

}
