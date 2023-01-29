package com.example.young.repository;

import com.example.young.entity.User;
import java.util.*;

public class UserRepoImpl implements UserRepo {
    private static Map<Long, User> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public User save(User user){
        user.setID(++sequence);
        store.put(user.getID(), user);
        return user;
    }

    @Override
    public Optional<User> findByID(Long ID) {
        return Optional.ofNullable(store.get(ID));
    }

    @Override
    public Optional<User> findByloginId(String loginId) {
        return store.values().stream().filter(user->user.getLoginId().equals(loginId)).findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
