package com.zhipuchina.wxpay.controller

import com.zhipuchina.wxpay.repository.network.model.ResultVo
import com.zhipuchina.wxpay.repository.network.model.TestModel
import com.zhipuchina.wxpay.repository.network.model.bsrequest.UnifiedOrderBsResponse
import com.zhipuchina.wxpay.repository.network.model.wxrequest.UnifiedOrder
import com.zhipuchina.wxpay.service.IWeChatPay
import kotlinx.coroutines.coroutineScope
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import kotlin.math.log

/**
 * 业务接口 只对应各个业务服务端 由业务服务端发起
 * @author  markrenChina
 */
@RestController
class Business constructor(
    @Autowired val weChatPayService: IWeChatPay
) {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)
    /**
     * 统一下单
     * 调用wechat http api 生成预付单 返回信息给业务端
     * （业务端生成支付参数并前面返回前端）
     * @doc https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1&index=1
     */
    @PostMapping("unifiedorder",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun unifiedOrder(@RequestBody @Validated unifiedOrder: UnifiedOrder):ResultVo<UnifiedOrderBsResponse> = coroutineScope{
        ResultVo(weChatPayService.unifiedOrder(unifiedOrder))
    }

    /*
     * webClient
     */
    @PostMapping("unifiedorder2")
    fun wcUnifiedOrder(@RequestBody @Validated unifiedOrder: Mono<UnifiedOrder>) = weChatPayService.unifiedOrder2(unifiedOrder).map { ResultVo(it) }



    @PreAuthorize("hasRole('user')")
    @MessageMapping("rs-unifiedorder")
    suspend fun rsUnifiedOrder(unifiedOrder: UnifiedOrder,@AuthenticationPrincipal user: UserDetails) = coroutineScope {
        user.authorities
        Flux.just(ResultVo(weChatPayService.unifiedOrder(unifiedOrder)))
    }

    @MessageMapping("rs-test")
    suspend fun testRsocket(testModel: TestModel) = coroutineScope {
        ResponseEntity<TestModel>(TestModel("test rsocke",777), HttpStatus.OK)
    }
    /**
     * 二次调用支付
     * 待确定
     * @param relationId 交易编号
     * @param type 支付类型
     * @return payment
     */

    /**
     * 允许业务端查询微信支付结果
     * @doc https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_2
     */
    @PostMapping("orderquery")
    suspend fun orderQuery()= coroutineScope{

    }

    /**
     * 申请退款
     * @doc https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_4
     */

    /**
     * 查询退款
     * @doc https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_5
     */

    /**
     * 拉取评价数据
     * @doc https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_17&index=11
     */
}