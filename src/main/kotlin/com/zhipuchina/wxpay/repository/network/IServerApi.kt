package com.zhipuchina.wxpay.repository.network

import com.zhipuchina.wxpay.repository.network.model.wxrequest.UnifiedOrder
import com.zhipuchina.wxpay.repository.network.model.wxresponse.UnifiedOrderWxResponse
import com.zhipuchina.wxpay.utils.WECAHT_HTTP_BASE_URL
import com.zhipuchina.wxpay.webClientRest.ApiServer
import org.springframework.http.MediaType.*
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import reactor.core.publisher.Mono


@ApiServer(WECAHT_HTTP_BASE_URL)
interface IServerApi {

    /**
     * consumes = [TEXT_PLAIN_VALUE] 表示服务端返回 默认json
     * produces = [APPLICATION_JSON_VALUE] 表示发出的格式 默认json
     */
    @PostMapping("/unifiedorder", consumes = [TEXT_XML_VALUE], produces = [TEXT_XML_VALUE])
    fun unifiedOrder(@RequestBody unifiedOrder: Mono<UnifiedOrder>): Mono<UnifiedOrderWxResponse>
}