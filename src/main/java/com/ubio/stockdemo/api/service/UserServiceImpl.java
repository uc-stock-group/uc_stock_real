package com.ubio.stockdemo.api.service;

import com.ubio.stockdemo.api.repository.UserRepository;
import com.ubio.stockdemo.api.util.EncryptionUtil;
import com.ubio.stockdemo.api.util.PasswordUtil;
import com.ubio.stockdemo.model.dto.AccessToken;
import com.ubio.stockdemo.model.dto.LoginDto;
import com.ubio.stockdemo.model.dto.LoginResponse;
import com.ubio.stockdemo.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApiService apiService;

    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    private EncryptionUtil encryptionUtil;



    @Override
    public ResponseEntity<User> create(User user) throws Exception {
        log.info("user : " + user);
//        TODO: check if user already exists

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

//        TODO: password encrypt

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

//        TODO: appkey, secretkey, cano encrypt

        //appkey 대칭키로 암호화
        user.setAppKey(encryptionUtil.encrypt(user.getAppKey()));

        //secretkey 대칭키로 암호화
        user.setAppSecret(encryptionUtil.encrypt(user.getAppSecret()));

        //cano encrypt
        user.setRealCano(encryptionUtil.encrypt(user.getRealCano()));

//        TODO: save user to database

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

//            accesstoken 발급받기
            AccessToken accessToken = apiService.getAccessToken(user.getAppKey(), user.getAppSecret());

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setId(user.getId());
            loginResponse.setUsername(user.getUsername());
            loginResponse.setAppKey(user.getAppKey());
            loginResponse.setAppSecret(user.getAppSecret());
            loginResponse.setRealCano(user.getRealCano());
            loginResponse.setAccessToken(accessToken);

            return loginResponse;
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }
}
