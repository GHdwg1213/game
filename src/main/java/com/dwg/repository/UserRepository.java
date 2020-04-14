package com.dwg.repository;


import com.dwg.model.entity.User;
import com.dwg.repository.base.BaseRepository;

public interface UserRepository extends BaseRepository<User, Integer> {

    User findByUsername(String username);

    User findByNickName(String nickName);
}