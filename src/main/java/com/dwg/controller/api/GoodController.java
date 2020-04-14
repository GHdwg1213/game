package com.dwg.controller.api;

import com.dwg.model.dto.MyShopDTO;
import com.dwg.model.support.BaseResponse;
import com.dwg.model.support.PageResult;
import com.dwg.service.GoodService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("good")
@RestController
public class GoodController {

    @Resource
    private GoodService goodService;

    @GetMapping("listMyBuy")
    public BaseResponse<PageResult<MyShopDTO>> listMyBuy(
            @PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable) {
        PageResult<MyShopDTO> result = goodService.listMyBuy(pageable);
        return BaseResponse.ok(result);
    }
}
