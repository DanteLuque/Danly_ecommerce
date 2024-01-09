package com.Danly.ecommerce.infrastructure.adapter;

import com.Danly.ecommerce.application.repository.UserRepository;
import com.Danly.ecommerce.domain.User;
import com.Danly.ecommerce.infrastructure.mapper.UserMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserCrudRepository userCrudRepository;
    private final UserMapper userMapper;

    public UserRepositoryImpl(UserCrudRepository userCrudRepository, UserMapper userMapper) {
        this.userCrudRepository = userCrudRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User createUser(User user) {
        return userMapper.toUser(userCrudRepository.save(userMapper.toUserEntity(user)));
    }

    @Override
    public User findByEmail(String email) {
        return userMapper.toUser(userCrudRepository.findByEmail(email).get());
    }

    @Override
    public User findById(Integer id) {
        return userMapper.toUser(userCrudRepository.findById(id).get());
    }
}
