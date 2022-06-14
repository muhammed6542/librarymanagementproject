package com.hexad.librarymanagement.service;

import com.hexad.librarymanagement.dto.UserDto;
import com.hexad.librarymanagement.entity.User;
import com.hexad.librarymanagement.exception.UserNotFoundException;
import com.hexad.librarymanagement.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User userOne;
    private User userTwo;
    private UserDto userDtoOne;
    private UserDto userDtoTwo;
    private List<User> userList;
    private List<UserDto> userDtoList;

    @BeforeEach
    public void setUp() {
        userList = new ArrayList<>();
        userDtoList = new ArrayList<>();

        userOne = createUser(1L);
        userDtoOne = new UserDto(userOne);

        userTwo = createUser(2L);
        userDtoTwo = new UserDto(userTwo);

        userList.add(userOne);
        userList.add(userTwo);

        userDtoList.add(userDtoOne);
        userDtoList.add(userDtoTwo);
    }

    @AfterEach
    public void tearDown() {
        userOne = userTwo = null;
        userList = null;
    }

    @Test
    public void testAddUser() {
        when(userRepository.save(any())).thenReturn(userOne);
        userService.addUser(userDtoOne);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void testExistById() {
        when(userRepository.existsById(1L)).thenReturn(true);
        assertThat(userService.userExist(userOne.getUserId())).isEqualTo(true);
    }

    @Test
    public void testNotExistById() {
        when(userRepository.existsById(1L)).thenReturn(false);
       assertThat(userService.userExist(userOne.getUserId())).isEqualTo(false);
    }

   

    @Test()
    public void testDeleteuserThrowException() {
        when(userRepository.existsById(1L)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser(1L);
        });
    }

    private User createUser(Long id) {
        User user = new User();
        user.setUserId(id);
        user.setUserName("OTHER BOOK");
        return user;

    }

}
