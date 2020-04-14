package com.dwg.model.dto;

import com.dwg.model.dto.base.OutputConverter;
import com.dwg.model.entity.Shop;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
@EqualsAndHashCode
public class ShopDTO implements OutputConverter<ShopDTO, Shop> {

    private Long id;

    private String gameName;

    private String gameType;

    private String gameServer;

    private String gameDetails;

    private BigDecimal gamePrice;
}
