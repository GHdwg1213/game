package com.dwg.model.entity;


import com.dwg.model.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Table(name = "good")
@Entity
@EqualsAndHashCode(callSuper = true)
public class Good extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long shopId;

    private Long buyUserId;

    private Integer gameHour;

    private Long endTime;

    /**
     * 0-订单进行中;1-订单已结束
     */
    private Integer status;
}
