package com.springboot.todo.repository.temp;

import com.springboot.todo.entity.User;

import java.util.*;

public class UserRepoImpl implements UserRepo{
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
        return Optional.ofNullable(store.get(userId));  // null이면 Optional.empty() 리턴
    }

    @Override
    public Optional<User> findByName(String userName) {
        return store.values().stream().filter(user -> user.getUserName().equals(userName)).findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
