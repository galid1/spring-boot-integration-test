package com.galid.jpastudy;

import com.galid.jpastudy.dto.CreateUserRequest;
import com.galid.jpastudy.dto.CreateUserResponse;
import com.galid.jpastudy.dto.GetUserListResponse;
import com.galid.jpastudy.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public CreateUserResponse createUser(CreateUserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/users")
    public GetUserListResponse getUserList() {
        return userService.getUserList();
    }

    @GetMapping("/users/{userId}")
    public UserDto getUser(@PathVariable("userId") Long userId) {
        return userService.getUser(userId);
    }
}
