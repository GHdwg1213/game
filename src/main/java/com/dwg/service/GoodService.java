package com.dwg.service;


import com.dwg.model.dto.MyShopDTO;
import com.dwg.model.entity.Good;
import com.dwg.model.support.PageResult;
import com.dwg.repository.GoodRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodService {

    @Resource
    private GoodRepository goodRepository;

    @Resource
    private UserService userService;

    @Resource
    private ShopService shopService;

    public void save(Good good) {
        goodRepository.save(good);
    }

    public PageResult<MyShopDTO> listMyBuy(Pageable pageable) {
        Long userId = userService.getUserId();
        List<Good> goods = goodRepository.findAllByBuyUserIdAndEndTimeGreaterThan(userId, System.currentTimeMillis());
        // 当前账号拥有的所有的shop
        List<Long> shopId = goods.stream().map(Good::getShopId).collect(Collectors.toList());
        PageResult<MyShopDTO> page = shopService.listBuyShop(shopId, pageable);
        goods.forEach(good -> page.getRows().forEach(row -> {
            if (good.getShopId().equals(row.getId())) {
                row.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(good.getEndTime())));
            }
        }));
        return new PageResult<>(page.getRows(), page.getTotalPage());
    }

    public List<Good> findAlreadyExpire() {
        return goodRepository.findAllByEndTimeLessThan(System.currentTimeMillis());
    }
}
