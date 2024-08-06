package com.ubio.stockdemo.api.service;

import com.ubio.stockdemo.api.repository.UserRepository;
import com.ubio.stockdemo.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    public User saveOrUpdateUser(String name, String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setName(name);
            user.setEmail(email);
        } else {
            user.setName(name);
        }
        return userRepository.save(user);
    }
}
