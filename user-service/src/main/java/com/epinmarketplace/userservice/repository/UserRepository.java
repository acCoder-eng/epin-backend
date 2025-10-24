package com.epinmarketplace.userservice.repository;

import com.epinmarketplace.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.username = :username OR u.email = :email")
    Optional<User> findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);
    
    @Query("SELECT u FROM User u WHERE u.isActive = true AND (u.username = :username OR u.email = :email)")
    Optional<User> findActiveUserByUsernameOrEmail(@Param("username") String username, @Param("email") String email);
    
    long countByIsActiveTrue();
}
