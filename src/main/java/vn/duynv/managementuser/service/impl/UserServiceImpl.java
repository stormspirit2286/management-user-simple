package vn.duynv.managementuser.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.duynv.managementuser.dto.request.UserCreationRequest;
import vn.duynv.managementuser.dto.response.UserDetailResponse;
import vn.duynv.managementuser.entity.User;
import vn.duynv.managementuser.exception.ResourceNotFoundException;
import vn.duynv.managementuser.mapper.UserMapper;
import vn.duynv.managementuser.repository.UserRepository;
import vn.duynv.managementuser.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetailResponse createUser(UserCreationRequest request) {
        User userSaved = userRepository.save(userMapper.toUser(request));
        return userMapper.toUserResponse(userSaved);
    }

    @Override
    public List<UserDetailResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users
                .stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetailResponse findUserById(Long userId) {
//        method 1
//        Optional<User> user = userRepository.findById(userId);
//        if (user.isPresent()) {
//            return userMapper.toUserResponse(user.get());
//        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        return userMapper.toUserResponse(user);
    }
}
