package com.zhipuchina.wxpay.repository

import com.zhipuchina.wxpay.repository.network.model.TestModel
import com.zhipuchina.wxpay.repository.network.model.wxrequest.UnifiedOrder
import com.zhipuchina.wxpay.repository.network.model.wxresponse.UnifiedOrderResponse

/**
 * 仓库接口
 * @author  markrenChina
 */
interface IRepository {

    /**
     * 统一下单
     */
    suspend fun unifiedOrder(unifiedOrder: UnifiedOrder): UnifiedOrderResponse
}
