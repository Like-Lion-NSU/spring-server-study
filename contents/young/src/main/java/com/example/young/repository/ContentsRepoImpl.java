package com.example.young.repository;

import com.example.young.entity.Contents;
import java.util.*;

public class ContentsRepoImpl implements ContentsRepo{
    private static Map<Long, Contents> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Contents save(Contents contents) {
        contents.setID(++sequence);
        store.put(contents.getID(), contents);
        return contents;
    }

    @Override
    public Optional<Contents> findbyID(Long ID) {
        return Optional.ofNullable(store.get(ID));
    }

    @Override
    public Optional<Contents> findByloginId(String loginId) {
        return store.values().stream().filter(user->user.getLoginId().equals(loginId)).findFirst();
    }

    @Override
    public List<Contents> findAll() {
        return null;
    }
}
