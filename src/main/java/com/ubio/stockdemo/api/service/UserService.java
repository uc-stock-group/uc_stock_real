package com.ubio.stockdemo.api.service;

import com.ubio.stockdemo.model.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    User saveOrUpdateUser(String name, String email);
}
