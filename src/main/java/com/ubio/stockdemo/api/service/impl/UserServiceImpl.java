package com.ubio.stockdemo.api.service.impl;

import com.ubio.stockdemo.api.repository.UserRepository;
import com.ubio.stockdemo.api.service.ApiService;
import com.ubio.stockdemo.api.service.UserService;
import com.ubio.stockdemo.api.util.EncryptionUtil;
import com.ubio.stockdemo.api.util.JwtUtil;
import com.ubio.stockdemo.api.util.PasswordUtil;

import com.ubio.stockdemo.model.dto.JwtDto;
import com.ubio.stockdemo.model.dto.LoginDto;
import com.ubio.stockdemo.model.dto.LoginResponse;
import com.ubio.stockdemo.model.dto.StockToken;
import com.ubio.stockdemo.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApiService apiService;

    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseEntity<User> create(User user) throws Exception {
        log.info("user : " + user);
//        TODO: check if user already exists

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

//        비밀번호 validation
        if (user.getHash().length() < 8) {
            return ResponseEntity.badRequest().build();
        }

        if (user.getHash().length() > 20) {
            return ResponseEntity.badRequest().build();
        }

//        비밀번호 hash PasswordUtil 사용
        user.setHash(passwordUtil.hashPassword(user.getHash()));

//        hash 대칭키로 암호화
        user.setHash(encryptionUtil.encrypt(user.getHash()));

        //appkey 대칭키로 암호화
        user.setAppKey(encryptionUtil.encrypt(user.getAppKey()));

        //secretkey 대칭키로 암호화
        user.setAppSecret(encryptionUtil.encrypt(user.getAppSecret()));

        //cano encrypt
        user.setRealCano(encryptionUtil.encrypt(user.getRealCano()));

        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<User> update(Long id, User user) {
        return null;
    }

    @Override
    public ResponseEntity<User> delete(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<User> findById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<User>> findAll() {
        return null;
    }

    @Override
    public ResponseEntity<List<User>> findAllPaging(int page, int size) {
        return null;
    }

    @Override
    public LoginResponse login(LoginDto loginDto) throws Exception {
        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (passwordUtil.checkPassword(loginDto.getPassword(), encryptionUtil.decrypt(user.getHash()))) {
            // 비밀번호가 일치하면 암호화된 데이터를 복호화합니다.
            user.setAppKey(encryptionUtil.decrypt(user.getAppKey()));
            user.setAppSecret(encryptionUtil.decrypt(user.getAppSecret()));
            user.setRealCano(encryptionUtil.decrypt(user.getRealCano()));

//            한투에서 accesstoken 발급받기
            StockToken stockToken = apiService.getAccessToken(user.getAppKey(), user.getAppSecret());
            
//          자체 Jwt 토큰 발급(토큰 안에 한투 토큰 넣기)
            String accessToken = jwtUtil.createToken(user, stockToken);

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setId(user.getId());
            loginResponse.setUsername(user.getUsername());
            loginResponse.setAccessToken(accessToken);
            return loginResponse;
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }
    @Override
    public ResponseEntity<String> validateToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        if (jwtUtil.isTokenExpired(token)) {
            log.info("Token expired");
//            TODO: 토큰 만료시 재발급
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expired");
        }
        return ResponseEntity.ok("Token is valid");
    }
}
