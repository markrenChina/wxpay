package com.zhipuchina.wxpay.repository

import com.zhipuchina.wxpay.repository.network.ApiConfig
import com.zhipuchina.wxpay.repository.network.ServiceCreator
import com.zhipuchina.wxpay.repository.network.model.wxrequest.UnifiedOrder
import com.zhipuchina.wxpay.repository.network.model.wxresponse.UnifiedOrderWxResponse
import org.springframework.stereotype.Repository

/**
 * api调用实现
 * Repository应该负责返回数据，
 * 内部处理是否是网络还是数据库
 * @author  markrenChina
 */
@Repository
class Repository: IRepository {

    private val netService = ServiceCreator.create<ApiConfig>()

    override suspend fun unifiedOrder(unifiedOrder: UnifiedOrder): UnifiedOrderWxResponse
    = netService.unifiedOrder(unifiedOrder)
}