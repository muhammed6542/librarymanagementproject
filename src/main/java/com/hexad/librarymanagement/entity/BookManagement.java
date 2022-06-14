package com.hexad.librarymanagement.entity;

import com.hexad.librarymanagement.enums.BookStatus;
import com.hexad.librarymanagement.enums.UserBookStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class BookManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookManagementId;
    private Long userId;
    private Long bookId;
    private BookStatus bookStatus;
    private UserBookStatus userBookStatus;

}
