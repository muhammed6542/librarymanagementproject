package com.hexad.librarymanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexad.librarymanagement.dto.UserDto;
import com.hexad.librarymanagement.exception.UserNotFoundException;
import com.hexad.librarymanagement.service.UserService;
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

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    private MockMvc mockMvc;

    UserDto userDto = createUserDto();

    @Test
    public void testGetUsers() throws Exception {
        mockMvc.perform(
                        get("/user/getAll")
                                .contentType(APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(userService, times(1)).getAllUser();
    }

    @Test
    public void testAddUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(userDto);

        mockMvc.perform(
                        post("/user/add")
                                .content(jsonString)
                                .contentType(APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(userService).addUser(any());
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc
                .perform(
                        delete("/user/1")
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    public void testDeleteUserThrowException() throws Exception {
        doThrow(new UserNotFoundException()).when(userService).deleteUser(1L);

        mockMvc.perform(
                        delete("/user/1")
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON))
                .andExpect(status().is(404));
    }


    @Test
    public void testUpdateUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(userDto);
        mockMvc.perform(
                        post("/user/update/1")
                                .content(jsonString)
                                .contentType(APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(userService, times(1)).updateUser(Mockito.anyLong(), any());
    }

    @Test
    public void testUpdateUserThrowException() throws Exception {
        doThrow(new UserNotFoundException()).when(userService).updateUser(any(), any());

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(userDto);
        mockMvc.perform(
                        post("/user/update/1").content(jsonString).contentType(APPLICATION_JSON))
                .andExpect(status().is(404));
    }



    private UserDto createUserDto() {
        return new UserDto(1L,
                "ONE USER"
        );
    }


}
