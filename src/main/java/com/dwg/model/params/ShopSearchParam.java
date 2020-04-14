package com.dwg.model.params;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ShopSearchParam {

    private String gameType;

    private String gamePrice;
}
