package com.galid.jpastudy;

import com.galid.jpastudy.dto.CreateUserRequest;
import com.galid.jpastudy.dto.CreateUserResponse;
import com.galid.jpastudy.dto.GetUserListResponse;
import com.galid.jpastudy.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public CreateUserResponse createUser(CreateUserRequest request) {
        UserEntity newUser = UserEntity.builder()
                .userName(request.getUserName())
                .build();
        Long userId = userRepository.save(newUser).getUserId();

        return new CreateUserResponse(userId);
    }

    public GetUserListResponse getUserList() {
        return new GetUserListResponse(userRepository.findAll().stream()
                .map(user -> new UserDto(user.getUserName()))
                .collect(Collectors.toList()));
    }


    public UserDto getUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        return new UserDto(userEntity.getUserName());
    }
}

