package com.dwg.repository;


import com.dwg.model.entity.UserMoney;
import com.dwg.repository.base.BaseRepository;

public interface UserMoneyRepository extends BaseRepository<UserMoney, Long> {

    UserMoney findByUserId(Long userId);
}