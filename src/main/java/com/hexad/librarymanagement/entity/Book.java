package com.hexad.librarymanagement.entity;

import com.hexad.librarymanagement.dto.BookDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;
    private String bookName;
    private String bookWriter;
    private Integer bookStock;
    private Integer copyStock;

    public Book(Long bookId, String bookName, String bookWriter, Integer bookStock, Integer copyStock) {
        super();
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookWriter = bookWriter;
        this.bookStock = bookStock;
        this.copyStock = copyStock;
    }

    public Book() {
    }

    public Book(BookDto bookDto){
        this.setBookStock(bookDto.getBookStock());
        this.setBookName(bookDto.getBookName());
        this.setCopyStock(bookDto.getCopyStock());
        this.setBookWriter(bookDto.getBookWriter());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return bookStock == book.bookStock &&
               copyStock==book.copyStock&&
                Objects.equals(bookId, book.bookId) &&
                Objects.equals(bookName, book.bookName) &&
                Objects.equals(bookWriter, book.bookWriter) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash( bookId,bookName,bookWriter,bookStock, copyStock);
    }
}
