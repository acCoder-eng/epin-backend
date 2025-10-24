package com.epinmarketplace.userservice.service;

import com.epinmarketplace.userservice.dto.UserRegistrationDto;
import com.epinmarketplace.userservice.dto.UserResponseDto;
import com.epinmarketplace.userservice.entity.User;
import com.epinmarketplace.userservice.repository.UserRepository;
import com.epinmarketplace.userservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    public UserResponseDto registerUser(UserRegistrationDto registrationDto) {
        // Check if username or email already exists
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        // Create new user
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registrationDto.getPassword()));
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setPhone(registrationDto.getPhone());
        user.setIsActive(true);
        user.setIsVerified(false);
        user.setRole(User.Role.USER);
        
        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser);
    }
    
    public String authenticateUser(String username, String password) {
        Optional<User> userOpt = userRepository.findActiveUserByUsernameOrEmail(username, username);
        
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        User user = userOpt.get();
        
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }
        
        return jwtUtil.generateToken(user.getUsername(), user.getRole().name());
    }
    
    @Transactional(readOnly = true)
    public UserResponseDto getUserByIdDto(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return new UserResponseDto(userOpt.get());
    }
    
    @Transactional(readOnly = true)
    public UserResponseDto getUserByUsername(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return new UserResponseDto(userOpt.get());
    }
    
    @Transactional(readOnly = true)
    public UserResponseDto getUserByEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return new UserResponseDto(userOpt.get());
    }
    
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<User> getAllUsersForAdmin() {
        return userRepository.findAll();
    }
    
    public UserResponseDto updateUser(Long id, UserRegistrationDto updateDto) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        User user = userOpt.get();
        
        // Update fields if provided
        if (updateDto.getFirstName() != null) {
            user.setFirstName(updateDto.getFirstName());
        }
        if (updateDto.getLastName() != null) {
            user.setLastName(updateDto.getLastName());
        }
        if (updateDto.getPhone() != null) {
            user.setPhone(updateDto.getPhone());
        }
        if (updateDto.getPassword() != null) {
            user.setPasswordHash(passwordEncoder.encode(updateDto.getPassword()));
        }
        
        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser);
    }
    
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
    
    public boolean deleteUserForAdmin(Long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }
    
    public User getUserByIdForAdmin(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    public User updateUserStatus(Long id, boolean isActive) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return null;
        }
        
        User user = userOpt.get();
        user.setIsActive(isActive);
        return userRepository.save(user);
    }
    
    public void deactivateUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        User user = userOpt.get();
        user.setIsActive(false);
        userRepository.save(user);
    }
    
    public void activateUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        User user = userOpt.get();
        user.setIsActive(true);
        userRepository.save(user);
    }
    
    public void verifyUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        User user = userOpt.get();
        user.setIsVerified(true);
        userRepository.save(user);
    }
    
    @Transactional(readOnly = true)
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }
    
    @Transactional(readOnly = true)
    public String getUsernameFromToken(String token) {
        return jwtUtil.extractUsername(token);
    }
    
    public UserResponseDto createAdminUser() {
        // Check if admin user already exists
        if (userRepository.existsByEmail("admin@epinmarketplace.com")) {
            throw new RuntimeException("Admin user already exists");
        }
        
        // Create admin user
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@epinmarketplace.com");
        admin.setPasswordHash(passwordEncoder.encode("password"));
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setPhone("+1234567890");
        admin.setIsActive(true);
        admin.setIsVerified(true);
        admin.setRole(User.Role.ADMIN);
        
        User savedAdmin = userRepository.save(admin);
        return new UserResponseDto(savedAdmin);
    }
}
