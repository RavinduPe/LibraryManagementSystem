package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.LoginRequest;
import com.example.LibraryManagementSystem.dto.RegisterRequest;
import com.example.LibraryManagementSystem.dto.AuthResponse;
import com.example.LibraryManagementSystem.dto.UserDto;
import com.example.LibraryManagementSystem.enums.Role;
import com.example.LibraryManagementSystem.entity.User;
import com.example.LibraryManagementSystem.repository.BorrowRepository;
import com.example.LibraryManagementSystem.repository.UserRepository;
import com.example.LibraryManagementSystem.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest registerRequest) {
        // Check if username exists
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        // Create new user account - always USER role
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.USER); // Always set to USER

        userRepository.save(user);

        // Generate token
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();

        String jwt = jwtUtil.generateToken(userDetails);

        return new AuthResponse(jwt, user.getId(), user.getUsername(), user.getRole().name());
    }

    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails);

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new AuthResponse(jwt, user.getId(), user.getUsername(), user.getRole().name());
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Admin methods for user management

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return convertToDto(user);
    }

    @Transactional
    public UserDto updateUserRole(Long id, Role newRole) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Don't allow changing the last admin's role
        if (user.getRole() == Role.ADMIN) {
            long adminCount = userRepository.findAll().stream()
                    .filter(u -> u.getRole() == Role.ADMIN)
                    .count();

            if (adminCount <= 1) {
                throw new RuntimeException("Cannot change the last admin's role");
            }
        }

        user.setRole(newRole);
        User updatedUser = userRepository.save(user);
        return convertToDto(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Don't allow deleting the last admin
        if (user.getRole() == Role.ADMIN) {
            long adminCount = userRepository.findAll().stream()
                    .filter(u -> u.getRole() == Role.ADMIN)
                    .count();

            if (adminCount <= 1) {
                throw new RuntimeException("Cannot delete the last admin");
            }
        }

        // Check if user has borrowed books
        if (borrowRepository.existsByUserAndReturnedFalse(user)) {
            throw new RuntimeException("Cannot delete user with active borrows");
        }

        userRepository.delete(user);
    }

    private UserDto convertToDto(User user) {
        int borrowedCount = borrowRepository.findByUserAndReturnedFalse(user).size();

        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                borrowedCount
        );
    }
}