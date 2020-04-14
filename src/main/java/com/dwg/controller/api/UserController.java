package com.dwg.controller.api;

import com.dwg.model.dto.UserDTO;
import com.dwg.model.entity.UserMoney;
import com.dwg.model.params.UserParam;
import com.dwg.model.support.BaseResponse;
import com.dwg.service.UserMoneyService;
import com.dwg.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 作用：用来处理用户表所有操作的Controller，Controller相当于Servlet的封装
 * 如果使用@Controller注解，返回的应该是一个字符串，这个字符串，就是templates里面的文件名，主要使用model对象将数据封装进去，然后进行渲染界面（后端操作）
 *
 * 如果使用@RestController注解，直接返回的是一个JSON字符串，相当于前后端分离（JSON是一个JavaScript对象，如果后端返回了一个JSON，那么就不用后端来管理界面的渲染了，前段使用JavaScript来进行渲染就可以了。）
 *
 * - @RequestMapping加在类上面的话，意味着是所有请求的前缀
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping(value = "addUser") // 等价于 @RequestMapping(method = RequestMethod.POST, value = "addUser")
    public BaseResponse<String> register(@Valid UserParam user) {
        userService.addUser(user);
        return BaseResponse.ok("恭喜您注册成功...");
    }

    @Resource
    private UserMoneyService userMoneyService;

    @GetMapping("getUserInfo")
    public UserDTO getUserInfo() {
        String nickName = userService.getNickName();
        UserMoney balance = userMoneyService.getBalance(userService.getUserId());
        return new UserDTO().setBalance(balance.getUserMoney()).setUsername(nickName);
    }
}
