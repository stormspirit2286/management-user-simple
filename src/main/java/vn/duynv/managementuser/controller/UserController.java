package vn.duynv.managementuser.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.duynv.managementuser.dto.request.UserCreationRequest;
import vn.duynv.managementuser.dto.response.ApiResponse;
import vn.duynv.managementuser.dto.response.UserDetailResponse;
import vn.duynv.managementuser.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<ApiResponse<UserDetailResponse>> createUser(@Valid @RequestBody UserCreationRequest request) {
        log.info("Creating new user: {}", request.getEmail());
        UserDetailResponse user = userService.createUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created("User created successfully", user));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<UserDetailResponse>>> getAllUsers() {
        log.info("Get all users");
        List<UserDetailResponse> users = userService.getAllUsers();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("Get all user successfully", users));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDetailResponse>> getUserById(@PathVariable("id") Long userId) {
        log.info("Get user with id: {}", userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("Get user successfully", userService.findUserById(userId)));
    }
}
