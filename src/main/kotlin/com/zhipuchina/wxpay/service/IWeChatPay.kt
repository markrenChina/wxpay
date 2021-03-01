package com.zhipuchina.wxpay.service

import com.zhipuchina.wxpay.repository.network.model.bsrequest.UnifiedOrderBsResponse
import com.zhipuchina.wxpay.repository.network.model.wxrequest.UnifiedOrder
import reactor.core.publisher.Mono

/**
 * 服务层接口
 * @author markrenChina
 */
interface IWeChatPay {

    /**
     * 验证业务请求，发送统一下单指令,返回支付参数并签名
     */
    suspend fun unifiedOrder(unifiedOrder: UnifiedOrder): UnifiedOrderBsResponse

    fun unifiedOrder2(unifiedOrder: Mono<UnifiedOrder>):Mono<UnifiedOrderBsResponse>
}