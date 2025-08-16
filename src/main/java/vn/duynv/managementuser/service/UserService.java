package vn.duynv.managementuser.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.duynv.managementuser.dto.request.UserCreationRequest;
import vn.duynv.managementuser.dto.response.PageResponse;
import vn.duynv.managementuser.dto.response.UserDetailResponse;

import java.util.List;

public interface UserService {
    UserDetailResponse createUser(UserCreationRequest request);
    List<UserDetailResponse> getAllUsers();
    UserDetailResponse findUserById(Long userId);
    List<UserDetailResponse> createMultipleUser(List<UserCreationRequest> requests);
    PageResponse<UserDetailResponse> getUsersWithPagination(int page, int size);
    Page<UserDetailResponse> getUserPage(Pageable pageable);
}
