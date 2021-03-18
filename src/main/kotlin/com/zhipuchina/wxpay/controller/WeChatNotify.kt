package com.zhipuchina.wxpay.controller

import com.zhipuchina.wxpay.config.UnifiedOrderDefConf
import com.zhipuchina.wxpay.repository.network.model.TestModel
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_XML_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
/**
 * 微信支付回调接口
 * @author  markrenChina
 */
class WeChatNotify {

    /**
     * url 是统一下单时设置的notify_url
     * 告知业务端结果
     * @return 告诉微信处理结果
     * consumes 消费xml
     * produces 返回xml
     */
    @PostMapping(value = ["wxpay1"])
    fun wxpay1() {

    }

    @GetMapping(value = ["demo"],consumes = [APPLICATION_XML_VALUE],produces = [APPLICATION_XML_VALUE])
    fun demo(@RequestBody testModel: TestModel): ResponseEntity<TestModel> {
        println("成功")
        println(testModel.name)
        return ResponseEntity<TestModel>(TestModel("test rsocke",777), HttpStatus.OK)
    }


    @GetMapping("test")
    fun test()= UnifiedOrderDefConf.attach+ " test"

    /**
     * 退款结果通知
     * @doc https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_16&index=10
     */
}