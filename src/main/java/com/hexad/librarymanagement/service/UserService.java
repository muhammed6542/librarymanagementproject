package com.hexad.librarymanagement.service;

import com.hexad.librarymanagement.dto.UserDto;
import com.hexad.librarymanagement.entity.User;
import com.hexad.librarymanagement.exception.BookNotFoundException;
import com.hexad.librarymanagement.exception.UserNotFoundException;
import com.hexad.librarymanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUser(Long userId) {
        User user = userRepository.findByUserId(userId);
        return new UserDto(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        if (userExist(userId))
            userRepository.deleteByUserId(userId);
        else throw new UserNotFoundException();
    }

    public List<UserDto> getAllUser() {

        return userRepository
                .findAll()
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    public void addUser(UserDto userDto) {
        User user = new User();
        user.setUserName(userDto.getUserName());
        userRepository.save(user);
    }

    public void updateUser(Long userId, UserDto userDto) {
        if (userExist(userId)) {
            User user = new User();
            user.setUserId(userId);
            user.setUserName(userDto.getUserName());
            userRepository.save(user);
        }
        else throw new UserNotFoundException();
    }

    public boolean userExist(Long bookId) {
        return userRepository.existsById(bookId);
    }

}
