package com.zhipuchina.wxpay.controller

import com.zhipuchina.wxpay.repository.network.model.TestModel
import org.springframework.http.MediaType.APPLICATION_XML_VALUE
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
/**
 * 微信支付回调接口
 * @author 任家立
 */
class WeChatNotify {

    /**
     * url 是统一下单时设置的notify_url
     * 告知业务端结果
     * @return 告诉微信处理结果
     * consumes 消费xml
     * produces 返回xml
     */
    @GetMapping(value = ["Demo"],consumes = [APPLICATION_XML_VALUE],produces = [APPLICATION_XML_VALUE])
    fun health(@RequestBody testModel: TestModel): TestModel {
        println("成功")
        println(testModel.name)
        return TestModel("123",98)
    }



    /**
     * 退款结果通知
     * @doc https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_16&index=10
     */
}