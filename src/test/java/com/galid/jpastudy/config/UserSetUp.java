package com.galid.jpastudy.config;

import com.galid.jpastudy.UserEntity;
import com.galid.jpastudy.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserSetUp {
    @Autowired
    private UserRepository userRepository;

    public Long saveUser(String userName) {
        UserEntity user = UserEntity.builder()
                .userName(userName)
                .build();
        return userRepository.save(user).getUserId();
    }
}
