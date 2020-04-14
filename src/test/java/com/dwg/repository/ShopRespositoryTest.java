package com.dwg.repository;

import com.dwg.model.entity.Shop;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.math.BigDecimal;

@SpringBootTest
@Slf4j
class ShopRespositoryTest {

    @Resource
    private ShopRepository shopRespository;

    @Test
    @Rollback(false)
    public void batchInsertShop() {
        for (int i = 0; i < 100; i++) {
            Shop shop = new Shop().setGameName("王者荣耀" + i)
                    .setGameDetails("全铭文，全皮肤" + i)
                    .setGamePrice(new BigDecimal(i))
                    .setGameServer(i + "区")
                    .setGameType(i % 2 == 0 ? "Android" : "IOS")
                    .setGameUsername("DWG" + i)
                    .setGameUserPassword("CY" + i)
                    .setStatus(0);
            shopRespository.save(shop);
            log.debug("添加成功:" + i);
        }
    }

}