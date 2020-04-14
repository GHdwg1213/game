package com.dwg.model.params;

import com.dwg.model.dto.base.InputConverter;
import com.dwg.model.entity.Shop;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ShopParam implements InputConverter<Shop> {

    private Long id;

    @NotBlank(message = "游戏名不能为空!")
    private String gameName;

    @NotBlank(message = "系统不能为空!")
    private String gameType;

    @NotBlank(message = "区服不能为空!")
    private String gameServer;

    @NotBlank(message = "账号不能为空!")
    private String gameUsername;

    @NotBlank(message = "密码不能为空!")
    private String gameUserPassword;

    private BigDecimal gamePrice;

    private Long userId;

    /**
     * 0-待租赁;1-租赁中(审核通过)
     */
    private Integer status;
}
