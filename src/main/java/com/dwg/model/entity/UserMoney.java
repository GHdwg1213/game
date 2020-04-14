package com.dwg.model.entity;


import com.dwg.model.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@Table(name = "userMoney")
@Entity
@EqualsAndHashCode(callSuper = true)
public class UserMoney extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private BigDecimal userMoney;
}
