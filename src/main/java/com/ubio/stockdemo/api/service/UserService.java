package com.ubio.stockdemo.api.service;

import com.ubio.stockdemo.model.dto.LoginDto;
import com.ubio.stockdemo.model.dto.LoginResponse;
import com.ubio.stockdemo.model.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<User> create(User user) throws Exception;

    ResponseEntity<User> update(Long id, User user);

    ResponseEntity<User> delete(Long id);

    ResponseEntity<User> findById(Long id);

    ResponseEntity<List<User>> findAll();

    ResponseEntity<List<User>> findAllPaging(int page, int size);

    LoginResponse login(LoginDto loginDto) throws Exception;
}
