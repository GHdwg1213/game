package com.dwg.service;

import com.dwg.exception.BadRequestException;
import com.dwg.model.entity.Good;
import com.dwg.model.entity.Shop;
import com.dwg.model.entity.UserMoney;
import com.dwg.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    @Resource
    private ShopService shopService;

    @Resource
    private UserMoneyService userMoneyService;

    @Resource
    private UserService userService;

    @Resource
    private GoodService goodService;

    @Transactional
    public void payment(Long shopId, Integer gameHour) throws Throwable {

        // 3.通过用户id提取userMoney表的userMoney
        // 查询一下用户的余额，如果余额不足，提示用户
        Long userId = userService.getUserId();
        UserMoney userMoney = userMoneyService.getBalance(userId);
        if (userMoney == null) {
            throw new BadRequestException("无法获取用户的余额");
        }

        // 4.通过ShopId提取shop表的gameprice和userid，计算totalMoney=gameprice*gamehour
        Shop shop = shopService.getGamePriceById(shopId);
        if (shop.getStatus() != 0) {
            throw new BadRequestException("改商品已经下架!");
        }
        if (userId.equals(shop.getUserId())) {
            throw new BadRequestException("您不能购买自己的商品");
        }
        BigDecimal totalMoney = shop.getGamePrice().multiply(new BigDecimal(gameHour));
        // 5.totalMoney大于userMoney则提示（“余额不足”，抛了一个异常）
        if (userMoney.getUserMoney().compareTo(totalMoney) < 0) {
            throw new BadRequestException("余额不足!");
        }

        // 扣除购买用户的钱
        // 若小于则通过登录id找到userMoney表单的userMoney减去toalMoney
        userMoney.setUserMoney(userMoney.getUserMoney().subtract(totalMoney));
        // 7.更新userMoney
        userMoneyService.update(userMoney);

        // 根据shopId更改shop表对应的商品status为1（已租赁）
        shopService.update(shop.setStatus(1));

        // 将shopId和购买userId在已购买表中对应起来，并且将过期时间计算出来存储到数据库
        Good good = new Good().setBuyUserId(userId).setShopId(shopId)
                .setEndTime(DateUtils.add(DateUtils.now(), gameHour, TimeUnit.HOURS).getTime())
                .setGameHour(gameHour).setStatus(0);
        goodService.save(good);
    }
}
