package com.dwg.model.params;

import com.dwg.model.dto.base.InputConverter;
import com.dwg.model.entity.User;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class UserParam implements InputConverter<User> {

    @NotBlank(message = "用户名不能为空!")
    private String username;

    @NotBlank(message = "密码不能为空!")
    @Size(max = 16, min = 6, message = "密码的长度在{min} - {max}之间")
    private String password;

    @NotBlank(message = "昵称不能为空!")
    private String nickName;
}
