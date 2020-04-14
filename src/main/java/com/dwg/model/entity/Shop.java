package com.dwg.model.entity;

import com.dwg.model.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@Table(name = "shop")
@Entity
@EqualsAndHashCode(callSuper = true)
public class Shop extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String gameName;

    private String gameType;

    private String gameServer;

    private String gameUsername;

    private String gameUserPassword;

    private String gameDetails;

    private BigDecimal gamePrice;

    /**
     * 0-待租赁;1-租赁中(审核通过)
     */
    private Integer status;
}
