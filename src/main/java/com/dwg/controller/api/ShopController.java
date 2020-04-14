package com.dwg.controller.api;

import com.dwg.model.dto.MyShopDTO;
import com.dwg.model.dto.ShopDTO;
import com.dwg.model.params.ShopParam;
import com.dwg.model.params.ShopSearchParam;
import com.dwg.model.support.BaseResponse;
import com.dwg.model.support.PageResult;
import com.dwg.service.ShopService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("shop")
public class ShopController {

    @Resource
    private ShopService shopService;

    /**
     * http://localhost:10010/shop/list
     *
     * @return ShopList
     */
    @GetMapping("list")
    public BaseResponse<PageResult<ShopDTO>> listShop(
            @PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable, ShopSearchParam searchParam) {
        return BaseResponse.ok(shopService.listShop(searchParam, pageable));
    }

    @GetMapping("listMyShop")
    public BaseResponse<PageResult<MyShopDTO>> listMyShop(
            @PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return BaseResponse.ok(shopService.listMyShop(pageable));
    }

    @PostMapping("addShop")
    // 2.将填写的表单传递给后台，使用一个对象（ShopParam）接收
    public BaseResponse<String> addShop(@Valid ShopParam shopParam) {
        shopService.addShop(shopParam);
        // 5.返回保存成功给前台
        return BaseResponse.ok("上架成功");
    }

    /**
     *
     * @param status true上架 false下架
     * @return
     */
    @PostMapping("changeShopStatus")
    public BaseResponse<String> changeStatus(Long shopId, Boolean status) throws Throwable {
        shopService.changeStatus(shopId, status);
        return BaseResponse.ok("状态更改成功!");
    }

    @PostMapping("deleteShop")
    public BaseResponse<String> deleteShop(Long shopId) throws Throwable {
        shopService.deleteShop(shopId);
        return BaseResponse.ok("删除成功!");
    }
}
