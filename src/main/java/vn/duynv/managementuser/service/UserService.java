package vn.duynv.managementuser.service;

import vn.duynv.managementuser.dto.request.UserCreationRequest;
import vn.duynv.managementuser.dto.response.UserDetailResponse;

import java.util.List;

public interface UserService {
    UserDetailResponse createUser(UserCreationRequest request);
    List<UserDetailResponse> getAllUsers();
    UserDetailResponse findUserById(Long userId);
}
