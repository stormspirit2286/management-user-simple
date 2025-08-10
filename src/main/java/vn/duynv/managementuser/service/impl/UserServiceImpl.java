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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @Override
    @Transactional
    public List<UserDetailResponse> createMultipleUser(List<UserCreationRequest> requests) {
        log.info("Creating {} users", requests.size());
        if (requests.isEmpty()) {
            throw new IllegalArgumentException("User creation requests cannot be empty");
        }

        validateNoDuplicatesInRequest(requests);
        validateUsersNotExist(requests);

        List<User> users = requests.stream().map(userMapper::toUser).toList();
        List<User> savedUsers = userRepository.saveAll(users);
        return savedUsers.stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    private void validateNoDuplicatesInRequest(List<UserCreationRequest> requests) {
        Set<String> emails = new HashSet<>();
        Set<String> usernames = new HashSet<>();

        for (UserCreationRequest request: requests) {
            if (!emails.add(request.getEmail())) {
                throw new IllegalArgumentException("Duplicate email in request: " + request.getEmail());
            }

            if (!usernames.add(request.getUsername())) {
                throw new IllegalArgumentException("Duplicate username in request: " + request.getUsername());
            }
        }
    }

    private void validateUsersNotExist(List<UserCreationRequest> requests) {
        Set<String> emails = requests.stream()
                .map(UserCreationRequest::getEmail)
                .collect(Collectors.toSet());
        Set<String> usernames = requests.stream()
                .map(UserCreationRequest::getUsername)
                .collect(Collectors.toSet());

        List<String> existingEmails = userRepository.findExistingEmail(emails);
        if (!existingEmails.isEmpty()) {
            throw new ResourceAlreadyExistsException("User already exist with emails: " + existingEmails);
        }

        List<String> existingUsernames = userRepository.findExistingUsernames(usernames);
        if (!existingEmails.isEmpty()) {
            throw new ResourceAlreadyExistsException("User already exist with usernames: " + existingUsernames);
        }
    }
}
