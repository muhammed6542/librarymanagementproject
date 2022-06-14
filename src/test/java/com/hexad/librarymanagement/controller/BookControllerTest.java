package com.hexad.librarymanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexad.librarymanagement.dto.BookDto;
import com.hexad.librarymanagement.exception.BookNotFoundException;
import com.hexad.librarymanagement.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @MockBean
    BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    BookDto bookDto = createBookDto();

    @Test
    public void testGetBooks() throws Exception {
        mockMvc.perform(
                        get("/book/getAll")
                                .contentType(APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(bookService, times(1)).getAllBook();
    }

    @Test
    public void testAddBook() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(bookDto);

        mockMvc.perform(
                        post("/book/add")
                                .content(jsonString)
                                .contentType(APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(bookService).addBook(any());
    }

    @Test
    public void testDeleteBook() throws Exception {
        mockMvc
                .perform(
                        delete("/book/1")
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(bookService, times(1)).deleteBook(1L);
    }

    @Test
    public void testDeleteBookThrowException() throws Exception {
        doThrow(new BookNotFoundException()).when(bookService).deleteBook(1L);

        mockMvc.perform(
                        delete("/book/1")
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    public void testUpdateBook() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(bookDto);
        mockMvc.perform(
                        post("/book/update/1")
                                .content(jsonString)
                                .contentType(APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(bookService, times(1)).updateBook(Mockito.anyLong(), any());
    }

    @Test
    public void testUpdateBookThrowException() throws Exception {
        doThrow(new BookNotFoundException()).when(bookService).updateBook(any(), any());

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(bookDto);
        mockMvc.perform(
                        post("/book/update/1").content(jsonString).contentType(APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    private BookDto createBookDto() {
        return new BookDto(
                1L,
                "ONE BOOK",
                "ONE WRITER",
                10,
                10
        );
    }


}
