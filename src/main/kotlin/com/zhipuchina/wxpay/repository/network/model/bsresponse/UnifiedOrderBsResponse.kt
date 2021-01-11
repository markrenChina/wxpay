package com.zhipuchina.wxpay.repository.network.model.bsresponse

import com.zhipuchina.wxpay.config.UnifiedOrderDefConf
import com.zhipuchina.wxpay.utils.SignUtils.encodeHMACSHA256
import com.zhipuchina.wxpay.utils.SignUtils.encodeMD5
import java.util.*

/**
 * 统一下单返回业务端（暂时对接的是小程序，Json）
 */
data class UnifiedOrderBsResponse(
    /**
     * 小程序ID
     */
    val appId : String,
    /**
     * prepay_id
     * 统一下单接口返回的 prepay_id 参数值
     */
    val prepay_id: String,
    /**
     *签名类型，
     * 默认为MD5，支持HMAC-SHA256和MD5。注意此处需与统一下单的签名类型一致
     */
    val signType: String,
    /**
     * 签名
     */
    var paySign: String?= null,
    /**
     * 随机字符串
     * 微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时
     */
    val nonceStr: String = UUID.randomUUID().toString().replace("-","").toUpperCase(),
    /**
     * 签名 时间戳
     * 发起支付时的appid
     */
    val timeStamp: String = (Date().time/1000).toString()
){
    init {
        //写死不用反射 执行快
        val date = StringBuilder().run {
            this.append("appId=").append(this@UnifiedOrderBsResponse.appId)
                .append("&nonceStr=").append(this@UnifiedOrderBsResponse.nonceStr)
                .append("&package=prepay_id=w").append(this@UnifiedOrderBsResponse.prepay_id)
                .append("&signType=").append(this@UnifiedOrderBsResponse.signType)
                .append("&timeStamp").append(this@UnifiedOrderBsResponse.timeStamp)
                .append("&key=").append(UnifiedOrderDefConf.apiKey).toString().toUpperCase()
        }
        paySign = when (signType){
            "MD5" -> encodeMD5(date)
            "HMAC-SHA256" -> encodeHMACSHA256(date,UnifiedOrderDefConf.apiKey)
            else -> null
        }
    }
}