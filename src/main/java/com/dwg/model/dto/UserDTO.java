package com.dwg.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class UserDTO {

    private String username;

    private BigDecimal balance;
}
