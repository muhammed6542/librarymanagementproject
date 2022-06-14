package com.hexad.librarymanagement.repository;

import com.hexad.librarymanagement.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository  extends CrudRepository<Book, Long> {

    Book findByBookId(Long bookId);
    void deleteByBookId(Long bookId);
    List<Book> findAll();

}