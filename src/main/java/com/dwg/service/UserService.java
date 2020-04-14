package com.dwg.service;


import com.dwg.exception.BadRequestException;
import com.dwg.model.entity.User;
import com.dwg.model.entity.UserMoney;
import com.dwg.model.params.UserParam;
import com.dwg.repository.UserMoneyRepository;
import com.dwg.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
@Slf4j
public class UserService {

    @Resource
    private UserRepository userRespository;

    @Resource
    private UserMoneyRepository userMoneyRepository;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    public void addUser(UserParam user) {
        User usernameUser = userRespository.findByUsername(user.getUsername());
        log.debug("用户名: {}, {}", user.getUsername(), usernameUser);
        if (usernameUser != null) {
            throw new BadRequestException("用户名已经被注册");
        }
        User nickNameUser = userRespository.findByNickName(user.getNickName());
        log.debug("昵称: {}, {}", user.getNickName(), nickNameUser);
        if (nickNameUser != null) {
            throw new BadRequestException("昵称已经被占用");
        }
        User userInfo = user.convertTo();
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfo.setRoles("normalUser");
        User user1 = userRespository.save(userInfo);
        Long userId = user1.getId();
        UserMoney userMoney = new UserMoney().setUserId(userId).setUserMoney(new BigDecimal("100"));
        userMoneyRepository.save(userMoney);
    }

    public String getNickName() {
        String username = getCurrentUserName();
        User user = userRespository.findByUsername(username);
        return user.getNickName();
    }

    public Long getUserId() {
        String username = getCurrentUserName();
        User user = userRespository.findByUsername(username);
        return user.getId();
    }

    public String getCurrentUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return null;
    }
}
