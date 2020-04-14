package com.dwg.model.dto;

import com.dwg.model.dto.base.OutputConverter;
import com.dwg.model.entity.Shop;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class MyShopDTO implements OutputConverter<MyShopDTO, Shop> {

    private Long id;

    private String gameName;

    private String gameType;

    private String gameServer;

    private String gameDetails;

    private BigDecimal gamePrice;

    private String gameUsername;

    private String gameUserPassword;

    private Integer status;

    private String endTime;
}
