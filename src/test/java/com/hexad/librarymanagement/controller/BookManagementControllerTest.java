package com.hexad.librarymanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexad.librarymanagement.dto.BookDto;
import com.hexad.librarymanagement.dto.BookManagementDto;
import com.hexad.librarymanagement.enums.BookStatus;
import com.hexad.librarymanagement.enums.UserBookStatus;
import com.hexad.librarymanagement.exception.BookNotFoundException;
import com.hexad.librarymanagement.exception.ConcurrentStockUpdateException;
import com.hexad.librarymanagement.exception.StockNotFoundException;
import com.hexad.librarymanagement.service.BookManagementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookManagementController.class)
public class BookManagementControllerTest {

    @MockBean
    BookManagementService bookManagementService;

    @Autowired
    private MockMvc mockMvc;

    BookManagementDto bookManagementDtoForBorrow = createdBookManagementDto(UserBookStatus.BORROW);
    BookManagementDto bookManagementDtoForView = createdBookManagementDto(UserBookStatus.VIEW);
    BookManagementDto bookManagementDtoForLendBack=createdBookManagementDto(UserBookStatus.LENDBACK);
    @Test
    public void testBorrowBookByUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(bookManagementDtoForBorrow);
        mockMvc.perform(
                        post("/bookManagement/borrowBookByUser")
                                .content(jsonString)
                                .contentType(APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(bookManagementService, times(1)).borrowBookByUsers(any());
    }

    @Test
    public void testBorrowBookByUserThrowStockNotFoundException() throws Exception {
        doThrow(new StockNotFoundException()).when(bookManagementService).borrowBookByUsers(any());

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(bookManagementDtoForBorrow);
        mockMvc.perform(
                        post("/bookManagement/borrowBookByUser").content(jsonString).contentType(APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    public void testBorrowBookByUserThrowBookNotFoundException() throws Exception {
        doThrow(new BookNotFoundException()).when(bookManagementService).borrowBookByUsers(any());

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(bookManagementDtoForBorrow);
        mockMvc.perform(
                        post("/bookManagement/borrowBookByUser").content(jsonString).contentType(APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    public void testBorrowBookByUserThrowConcurrentStockUpdateException() throws Exception {
        doThrow(new ConcurrentStockUpdateException()).when(bookManagementService).borrowBookByUsers(any());

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(bookManagementDtoForBorrow);
        mockMvc.perform(
                        post("/bookManagement/borrowBookByUser").content(jsonString).contentType(APPLICATION_JSON))
                .andExpect(status().is(403));
    }




    @Test
    public void testViewBookByUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(bookManagementDtoForView);
        mockMvc.perform(
                        post("/bookManagement/viewBookByUsers")
                                .content(jsonString)
                                .contentType(APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(bookManagementService, times(1)).viewBookByUsers(any());
    }

    @Test
    public void testViewBookByUserThrowStockNotFoundException() throws Exception {
        doThrow(new StockNotFoundException()).when(bookManagementService).viewBookByUsers(any());

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(bookManagementDtoForView);
        mockMvc.perform(
                        post("/bookManagement/viewBookByUsers").content(jsonString).contentType(APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    public void testViewBookByUserThrowBookNotFoundException() throws Exception {
        doThrow(new BookNotFoundException()).when(bookManagementService).viewBookByUsers(any());

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(bookManagementDtoForView);
        mockMvc.perform(
                        post("/bookManagement/viewBookByUsers").content(jsonString).contentType(APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    public void testViewBookByUserThrowConcurrentStockUpdateException() throws Exception {
        doThrow(new ConcurrentStockUpdateException()).when(bookManagementService).viewBookByUsers(any());

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(bookManagementDtoForView);
        mockMvc.perform(
                        post("/bookManagement/viewBookByUsers").content(jsonString).contentType(APPLICATION_JSON))
                .andExpect(status().is(403));
    }








    @Test
    public void testLendBackBookByUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(bookManagementDtoForLendBack);
        mockMvc.perform(
                        post("/bookManagement/lendBackBookByUsers")
                                .content(jsonString)
                                .contentType(APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(bookManagementService, times(1)).lendBackBookByUsers(any());
    }

    @Test
    public void testLendBackBookByUserThrowStockNotFoundException() throws Exception {
        doThrow(new StockNotFoundException()).when(bookManagementService).lendBackBookByUsers(any());

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(bookManagementDtoForLendBack);
        mockMvc.perform(
                        post("/bookManagement/lendBackBookByUsers").content(jsonString).contentType(APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    public void testLendBackBookByUserThrowBookNotFoundException() throws Exception {
        doThrow(new BookNotFoundException()).when(bookManagementService).lendBackBookByUsers(any());

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(bookManagementDtoForLendBack);
        mockMvc.perform(
                        post("/bookManagement/lendBackBookByUsers").content(jsonString).contentType(APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    public void testLendBackBookByUserThrowConcurrentStockUpdateException() throws Exception {
        doThrow(new ConcurrentStockUpdateException()).when(bookManagementService).lendBackBookByUsers(any());

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(bookManagementDtoForLendBack);
        mockMvc.perform(
                        post("/bookManagement/lendBackBookByUsers").content(jsonString).contentType(APPLICATION_JSON))
                .andExpect(status().is(403));
    }



    @Test
    public void testGetUsersBook() throws Exception {


        mockMvc.perform(
                        get("/bookManagement/getUsersBook?userId=1&userBookStatus=BORROW")
                                .contentType(APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(bookManagementService, times(1)).getUsersBook(any(),any());
    }

    @Test
    public void testGetBookBoughtByTheUser() throws Exception {


        mockMvc.perform(
                        get("/bookManagement/getBookBoughtByTheUser?bookId=1&userBookStatus=BORROW")
                                .contentType(APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(bookManagementService, times(1)).getBookBoughtByTheUser(any(),any());
    }





    private BookManagementDto createdBookManagementDto(
            UserBookStatus userBookStatus) {

        return new BookManagementDto(
                1L,
                1L,
                BookStatus.COPY,
                userBookStatus
        );
    }


}
