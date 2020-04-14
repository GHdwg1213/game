package com.dwg.model.entity;

import com.dwg.model.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Table(name = "dictionary")
@Entity
@EqualsAndHashCode(callSuper = true)
public class Dictionary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long typeId;

    private String name;

    private Integer status;
}
