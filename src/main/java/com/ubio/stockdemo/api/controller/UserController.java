package com.ubio.stockdemo.api.controller;

import com.ubio.stockdemo.api.service.UserService;
import com.ubio.stockdemo.model.dto.LoginDto;
import com.ubio.stockdemo.model.dto.LoginResponse;
import com.ubio.stockdemo.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

        @Autowired
        private UserService userService;

        @PostMapping
        public ResponseEntity<User> create(@RequestBody User user) throws Exception {
            log.info("user : " + user);
            return userService.create(user);
        }

        @PutMapping("/{id}")
        public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
            return userService.update(id, user);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<User> delete(@PathVariable Long id) {
            return userService.delete(id);
        }

        @GetMapping("/{id}")
        public ResponseEntity<User> find(@PathVariable Long id) {
            return userService.findById(id);
        }

        @GetMapping("/all")
        public ResponseEntity<List<User>> findAll() {
            return userService.findAll();
        }

        @GetMapping("/all/paging")
        public ResponseEntity<List<User>> findAllPaging(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size
        ) {
            return userService.findAllPaging(page, size);
        }

        @PostMapping("/login")
        public LoginResponse login(@RequestBody LoginDto loginDto) throws Exception {
            return userService.login(loginDto);
        }
}
