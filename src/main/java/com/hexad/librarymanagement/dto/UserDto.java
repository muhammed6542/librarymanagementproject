package com.hexad.librarymanagement.dto;

import com.hexad.librarymanagement.entity.BookManagement;
import com.hexad.librarymanagement.entity.User;
import com.hexad.librarymanagement.enums.BookStatus;
import com.hexad.librarymanagement.enums.UserBookStatus;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long userId;
    private @NotNull String userName;

    public UserDto(Long userId , @NotNull String userName) {
        this.userId=userId;
        this.userName = userName;
    }

    public UserDto(){

    }

    public  UserDto(User user){
        this.setUserId(user.getUserId());
        this.setUserName(user.getUserName());
    }
}
