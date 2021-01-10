package com.zhipuchina.wxpay.repository.network.model.wxresponse

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

/**
 * 统一下单微信返回
 * @author markrenChina
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
class UnifiedOrderResponse {
    /**
     * 返回状态码
     * SUCCESS/FAIL
     * 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
     */
    @XmlElement(name = "return_code")
    var returnCode: String= ""
    /**
     * 返回信息
     */
    @XmlElement(name = "return_msg")
    var returnMsg: String?= null
    //***********以下字段在return_code为SUCCESS的时候有返回***********
    /**
     * 小程序ID
     * 调用接口提交的小程序ID
     */
    var appid: String= ""
    /**
     * 商户号
     * 调用接口提交的商户号
     */
    @XmlElement(name = "mch_id")
    var mchId: String= ""
    /**
     * 设备号
     * 自定义参数，可以为请求支付的终端设备号等
     */
    @XmlElement(name = "device_info")
    var deviceInfo: String?= null
    /**
     * 随机字符串
     * 微信返回的随机字符串
     */
    @XmlElement(name = "nonce_str")
    var nonceStr: String= ""
    /**
     * 签名
     * 微信返回的签名值
     */
    var sign = ""
    /**
     * 业务结果
     * SUCCESS/FAIL
     */
    @XmlElement(name = "result_code")
    var resultCode= ""
    /**
     * 错误代码 详细参见错误列表
     */
    @XmlElement(name = "err_code")
    var errCode: String?= null
    /**
     * 错误代码描述
     * 错误信息描述
     */
    @XmlElement(name = "err_code_des")
    var errCodeDes: String?= null
    //*********以下字段在return_code 和result_code都为SUCCESS的时候有返回*******
    /**
     * 交易类型
     * 交易类型，取值为：JSAPI，NATIVE，APP等
     * 必填
     */
    @XmlElement(name = "trade_type")
    var tradeType: String?= null
    /**
     * 预支付交易会话标识
     * 微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时
     * 必填
     */
    @XmlElement(name = "prepay_id")
    var prepayId: String?= null
    /**
     * 二维码链接
     * trade_type=NATIVE时有返回，此url用于生成支付二维码，然后提供给用户进行扫码支付。
     * 注意：code_url的值并非固定，使用时按照URL格式转成二维码即可
     */
    @XmlElement(name = "code_url")
    var codeUrl: String?= null
}