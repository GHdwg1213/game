package com.dwg.controller.api;

import com.dwg.model.support.BaseResponse;
import com.dwg.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("payment")
public class PaymentController {

    @Resource
    private PaymentService paymentService;


    @PostMapping("buyShop")
    // 传递shopId和gameHour给后端
    public BaseResponse<String> payment(Long shopId, Integer gameHour) throws Throwable {
        paymentService.payment(shopId, gameHour);
        return BaseResponse.ok("支付成功!");
    }

    // 时间结束，shopI的对应的产品状态变为下架状态

    //
}
