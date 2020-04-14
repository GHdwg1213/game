package com.dwg.service;


import com.dwg.exception.BadRequestException;
import com.dwg.model.dto.MyShopDTO;
import com.dwg.model.dto.ShopDTO;
import com.dwg.model.entity.Shop;
import com.dwg.model.params.ShopParam;
import com.dwg.model.params.ShopSearchParam;
import com.dwg.model.support.PageResult;
import com.dwg.repository.ShopRepository;
import com.dwg.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ShopService {

    @Resource
    private ShopRepository shopRepository;


    /**
     * 查询所有的商品，然后到前台展示!
     *
     * @return 所有的商品
     */
    public PageResult<ShopDTO> listShop(ShopSearchParam searchParam, Pageable pageable) {
        Long userId = userService.getUserId();
        Page<Shop> page;
        if (StringUtils.isEmpty(searchParam.getGameType())) {
            searchParam.setGameType("Android");
        }
        String gamePrice = searchParam.getGamePrice();
        if (!StringUtils.isEmpty(gamePrice)) {
            log.debug("查询的价格区间：" + gamePrice);
            String[] minMax = gamePrice.split("-");
            BigDecimal min = new BigDecimal(minMax[0]);
            BigDecimal max = new BigDecimal(minMax[1]);
            page = shopRepository.findAllByGameTypeAndGamePriceBetweenAndStatusAndUserIdNotAndIsDelete(searchParam.getGameType(), min, max, 0, userId, false, pageable);
        } else {
            page = shopRepository.findAllByGameTypeAndStatusAndUserIdNotAndIsDelete(searchParam.getGameType(), 0, userId, false, pageable);
        }
        long totalElements = page.getTotalElements();
        List<ShopDTO> collect = page.stream().map(this::convertToDto).collect(Collectors.toList());
        return new PageResult<>(collect, totalElements);
    }

    public PageResult<MyShopDTO> listMyShop(Pageable pageable) {
        Long userId = userService.getUserId();
        Page<Shop> shops = shopRepository.findAllByUserIdAndIsDelete(userId, false, pageable);
        List<MyShopDTO> rows = shops.stream().map(this::convertToMyShopDto).collect(Collectors.toList());
        long total = shops.getTotalElements();
        return new PageResult<>(rows, total);
    }

    public PageResult<MyShopDTO> listBuyShop(List<Long> shopIds, Pageable pageable) {
        Page<Shop> shops = shopRepository.findAllByIdInAndIsDelete(shopIds, false, pageable);
        List<MyShopDTO> rows = shops.stream().map(this::convertToMyShopDto).collect(Collectors.toList());
        long total = shops.getTotalElements();
        return new PageResult<>(rows, total);
    }

    public MyShopDTO convertToMyShopDto(Shop shop) {

        Assert.notNull(shop, "Attachment must not be null");
        // Convert to output dto
        return new MyShopDTO().convertFrom(shop);
    }

    public ShopDTO convertToDto(Shop shop) {

        Assert.notNull(shop, "Attachment must not be null");
        // Convert to output dto
        return new ShopDTO().convertFrom(shop);
    }

    @Resource
    private UserService userService;

    public void addShop(ShopParam shopParam) {
        //后台转换params到entity
        Shop shop = shopParam.convertTo();
        // 3.设置status状态为0，并且获取当前登录用户的ID，设置到Shop中
        shop.setStatus(0).setUserId(userService.getUserId());
        if (shopParam.getId() != null) {
            Shop one = shopRepository.getOne(shopParam.getId());
            BeanUtils.updateProperties(shop, one);
            shopRepository.saveAndFlush(one);
        } else {
            shop.setIsDelete(false);
            // 调用Repository保存这个对象到数据库
            shopRepository.saveAndFlush(shop);
        }
    }

    public Shop getGamePriceById(Long id) throws Throwable {
        return shopRepository.findById(id).orElseThrow((Supplier<Throwable>) () -> new RuntimeException("无此商品！"));
    }

    public void update(Shop shop) {
        shopRepository.saveAndFlush(shop);
    }

    public void changeStatus(Long shopId, Boolean status) throws Throwable {
        Shop shop = shopRepository.findById(shopId).orElseThrow((Supplier<Throwable>) () -> new BadRequestException("没有该商品!"));
        shopRepository.saveAndFlush(shop.setStatus(status ? 0 : 1));
    }

    public void deleteShop(Long shopId) throws Throwable {
        Shop shop = shopRepository.findById(shopId).orElseThrow((Supplier<Throwable>) () -> new BadRequestException("没有该商品!"));
        shop.setIsDelete(true);
        shopRepository.saveAndFlush(shop);
    }

    public Shop getShopByShopId(Long shopId) {
        return shopRepository.findById(shopId).orElse(null);
    }
}
