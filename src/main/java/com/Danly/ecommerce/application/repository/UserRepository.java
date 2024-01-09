package com.Danly.ecommerce.application.repository;

import com.Danly.ecommerce.domain.User;
import org.springframework.stereotype.Repository;


public interface UserRepository {
    public User createUser(User user);
    public User findByEmail(String email);
    public User findById(Integer id);
}
