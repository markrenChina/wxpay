package com.zhipuchina.wxpay.controller

import com.zhipuchina.wxpay.repository.network.model.ResultVo
import com.zhipuchina.wxpay.repository.network.model.wxrequest.UnifiedOrder
import com.zhipuchina.wxpay.service.IWeChatPay
import kotlinx.coroutines.coroutineScope
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * 业务接口 只对应各个业务服务端 由业务服务端发起
 * @author  markrenChina
 */
@RestController
class Business constructor(
    @Autowired val weChatPayService: IWeChatPay
) {

    /**
     * 统一下单
     * 调用wechat http api 生成预付单 返回信息给业务端
     * （业务端生成支付参数并前面返回前端）
     * @doc https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1&index=1
     */
    @PostMapping("unifiedorder")
    suspend fun unifiedOrder(@RequestBody unifiedOrder: UnifiedOrder):ResultVo<String> = coroutineScope{
        ResultVo(weChatPayService.unifiedOrder(unifiedOrder))
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