package com.repository;

import com.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserRepolmpl implements UserRepo{

    private static Map<Long, User> store = new HashMap<>();
    private static long sequence = 0L;


    @Override
    public User save(User user) {
        user.setId(++sequence);
        store.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(String userId) {
        return Optional.ofNullable(store.get(userId));
    }

    @Override
    public Optional<User> findByName(String userName) {
        return store.values().stream()
                .filter(user -> user.getUserName().equals(userName))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    public void clearStore(){
        store.clear();
    }
}
