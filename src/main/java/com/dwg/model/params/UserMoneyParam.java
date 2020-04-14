package com.dwg.model.params;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class UserMoneyParam {

    private Long id;

    private BigDecimal userMoney;
}
