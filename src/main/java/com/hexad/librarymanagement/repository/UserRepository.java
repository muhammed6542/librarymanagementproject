package com.hexad.librarymanagement.repository;

import com.hexad.librarymanagement.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository  extends CrudRepository<User, Long> {

    User findByUserId(Long userId);
    List<User> findAll();
    void deleteByUserId(Long userId);


}
