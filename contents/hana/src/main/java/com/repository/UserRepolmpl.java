package com.repository;

import com.entity.User;

import java.util.*;

public abstract class UserRepolmpl implements UserRepo{

    private static Map<Long, User> store = new HashMap<>();
    private static long sequence = 0L;


    @Override
    public User save(User user) {
        user.setId(++sequence);
        store.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }


    @Override
    public Optional<User> findByUserId(String userId) {
        return store.values().stream().filter(user->user.getUserId().equals(userId)).findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }

}



