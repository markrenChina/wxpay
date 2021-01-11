package com.zhipuchina.wxpay.repository.network

import com.zhipuchina.wxpay.repository.network.model.wxrequest.UnifiedOrder
import com.zhipuchina.wxpay.repository.network.model.wxresponse.UnifiedOrderWxResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * api接口配置
 * @author  markrenChina
 */
interface ApiConfig {

    /*@POST("/testServer")
    suspend fun testServer(@Body testModel: TestModel): String

    @GET("/test")
    suspend fun test(): TestModel

    @POST("/test")
    suspend fun testPost(@Body testModel: UnifiedOrder): TestModel*/

    @POST("unifiedorder")
    suspend fun unifiedOrder(@Body unifiedOrder: UnifiedOrder): UnifiedOrderWxResponse
}