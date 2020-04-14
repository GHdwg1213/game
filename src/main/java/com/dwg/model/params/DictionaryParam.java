package com.dwg.model.params;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
public class DictionaryParam {

    private Long id;

    private Long typeId;

    private String name;

    private Integer status;
}
