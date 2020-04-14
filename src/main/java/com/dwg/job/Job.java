package com.dwg.job;

import com.dwg.model.entity.Good;
import com.dwg.model.entity.Shop;
import com.dwg.model.entity.UserMoney;
import com.dwg.service.GoodService;
import com.dwg.service.ShopService;
import com.dwg.service.UserMoneyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Component
@EnableScheduling//可以在启动类上注解也可以在当前文件
@Slf4j
public class Job {

    @Resource
    private GoodService goodService;

    @Resource
    private ShopService shopService;

    @Resource
    private UserMoneyService userMoneyService;

    /**
     * 查询租赁到期的订单,变更订单状态,变更商品状态,变更商品上架用户的money
     */
    @Scheduled(cron = "1/60 * * * * ?")
    @Transactional
    public void runInOneSecond() {
        log.debug("执行了一次加钱操作...");
        List<Good> alreadyExpire = goodService.findAlreadyExpire();
        if (alreadyExpire == null) {
            return;
        }
        for (Good good : alreadyExpire) {
            if (good.getStatus() == 1) {
                return;
            }
            Shop shop = shopService.getShopByShopId(good.getShopId());
            if (shop == null) {
                log.debug("商品为null");
                return;
            }
            BigDecimal money = shop.getGamePrice().multiply(new BigDecimal(good.getGameHour()));
            Long userId = shop.getUserId();
            UserMoney balance = userMoneyService.getBalance(userId);
            balance.setUserMoney(balance.getUserMoney().add(money));
            userMoneyService.update(balance);
            good.setStatus(1);
            goodService.save(good);
        }
    }
}