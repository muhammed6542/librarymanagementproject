package com.hexad.librarymanagement.dto;

import com.hexad.librarymanagement.entity.Book;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class BookDto {
    private Long bookId;
    private @NotNull  String bookName;
    private @NotNull String bookWriter;
    private @NotNull  Integer bookStock;
    private @NotNull Integer copyStock;

    public BookDto() {
      }


    public BookDto(Long bookId , @NotNull  String bookName, @NotNull  String bookWriter,@NotNull  Integer bookStock,@NotNull  Integer copyStock) {
        this.bookId=bookId;
        this.bookName = bookName;
        this.bookWriter = bookWriter;
        this.bookStock = bookStock;
        this.copyStock = copyStock;
    }


    public BookDto(Book book) {
        this.setBookName(book.getBookName());
        this.setBookWriter(book.getBookWriter());
        this.setBookStock(book.getBookStock());
        this.setCopyStock(book.getCopyStock());
        this.setBookId(book.getBookId());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto book = (BookDto) o;
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
