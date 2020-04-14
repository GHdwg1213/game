package com.dwg.service;


import com.dwg.model.entity.UserMoney;
import com.dwg.repository.UserMoneyRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserMoneyService {

    @Resource
    private UserMoneyRepository userMoneyRepository;

    public UserMoney getBalance(Long userId) {
        return userMoneyRepository.findByUserId(userId);
    }

    public void update(UserMoney userMoney) {
        userMoneyRepository.save(userMoney);
    }
}
