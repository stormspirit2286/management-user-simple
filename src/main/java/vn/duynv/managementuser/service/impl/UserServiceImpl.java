package vn.duynv.managementuser.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.duynv.managementuser.dto.request.UserCreationRequest;
import vn.duynv.managementuser.dto.response.UserDetailResponse;
import vn.duynv.managementuser.entity.User;
import vn.duynv.managementuser.exception.ResourceAlreadyExistsException;
import vn.duynv.managementuser.exception.ResourceNotFoundException;
import vn.duynv.managementuser.mapper.UserMapper;
import vn.duynv.managementuser.repository.UserRepository;
import vn.duynv.managementuser.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDetailResponse createUser(UserCreationRequest request) {
        log.info("Creating new user: {}", request.getEmail());
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("User", "email", request.getEmail());
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResourceAlreadyExistsException("User", "username", request.getUsername());
        }
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
