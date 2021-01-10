package com.zhipuchina.wxpay.config

import com.zhipuchina.wxpay.repository.network.model.wxrequest.SceneInfo
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "def-order-conf")
object UnifiedOrderDefConf{
    /**
     * 小程序id String(32)
     * 微信分配的小程序ID
     */
    var appid: String = ""
    /**
     * 商户号 String(32)
     * 微信支付分配的商户号
     */
    var mchId: String = ""
    /**
     * 设备号 String(32)
     * 自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
     */
    var deviceInfo: String?= null
    /**
     * 签名类型 String(32)
     * 签名类型，默认为MD5，支持HMAC-SHA256和MD5。
     */
    var signType: String?= "MD5"
    /**
     * 商品描述 String(128)
     * 商品简单描述，该字段请按照规范传递，具体请见
     * 参数规定 https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=4_2
     */
    var body: String = ""
    /**
     * 商品详情 String(6000)
     * 商品详细描述，对于使用单品优惠的商户，该字段必须按照规范上传，详见
     * “单品优惠参数说明” https://pay.weixin.qq.com/wiki/doc/api/danpin.php?chapter=9_102&index=2
     */
    var detail: String?= null
    /**
     * 附加数据 String(127)
     * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用
     */
    var attach: String?= null
    /**
     * 商户订单号 String(32)
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一。详见
     * 商户订单号 https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=4_2
     */
    var outTradeNo: String = ""
    /**
     * 标价币种 String(16)
     * 符合ISO 4217标准的三位字母代码，默认人民币：CNY，详细列表请参见
     * 货币类型 境内商户号仅支持人民币 CNY：人民币
     */
    var feeType: String?=null
    /**
     * 标价金额
     * 订单总金额，单位为分，详见
     * 支付金额
     * 交易金额默认为人民币交易，接口中参数支付金额单位为【分】，参数值不能带小数。对账单中的交易金额单位为【元】。
     * 外币交易的支付金额精确到币种的最小单位，参数值不能带小数点。
     */
    var totalFee: Int = 0
    /**
     * 终端IP String(64)
     * 支持IPV4和IPV6两种格式的IP地址。调用微信支付API的机器IP
     */
    var spbillCreateIp: String=""
    /**
     * 交易起始时间 String(14)
     * 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见
     * 时间规则
     * 标准北京时间，时区为东八区，自1970年1月1日 0点0分0秒以来的秒数。注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)
     */
    var timeStart: String?= null
    /**
     * 交易结束时间 String(14)
     * 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。
     * 订单失效时间是针对订单号而言的，由于在请求支付的时候有一个必传参数prepay_id只有两小时的有效期，
     * 所以在重入时间超过2小时的时候需要重新请求下单接口获取新的prepay_id。其他详见时间规则
     * 建议：最短失效时间间隔大于1分钟
     */
    var timeExpire: String?= null
    /**
     * 订单优惠标记 String(32)
     * 订单优惠标记，使用代金券或立减优惠功能时需要的参数，说明详见
     * 代金券或立减优惠 https://pay.weixin.qq.com/wiki/doc/api/tools/sp_coupon.php?chapter=12_1
     */
    var goodsTag: String?= null
    /**
     * 通知地址 String(256)
     * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
     */
    var notifyUrl: String = ""
    /**
     * 交易类型 String(16)
     * JSAPI--JSAPI支付（或小程序支付）、NATIVE--Native支付、APP--app支付，MWEB--H5支付，
     * 不同trade_type决定了调起支付的方式，请根据支付产品正确上传
     * MICROPAY--付款码支付，付款码支付有单独的支付接口，所以接口不需要上传，该字段在对账单中会出现
     * 小程序取值如下：JSAPI，详细说明见
     */
    var tradeType: String = ""
    /**
     * 商品ID String(32)
     * trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
     */
    var productId: String?= null
    /**
     * 指定支付方式 String(32)
     * 上传此参数no_credit--可限制用户不能使用信用卡支付
     */
    var limitPay: String?= null
    /**
     * 用户标识 String(128)
     * openid
     * trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考
     * 获取openid https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html
     */
    var openid: String?= null
    /**
     * 电子发票入口开放标识 String(8)
     * Y，传入Y时，支付成功消息和支付详情页将出现开票入口。需要在微信支付商户平台或微信公众平台开通电子发票功能，传此字段才可生效
     */
    var receipt: String?= null
    /**
     * 是否需要分账 String(16)
     * Y-是，需要分账 N-否，不分账 字母要求大写，不传默认不分账
     */
    var profitSharing: String?= null
    /**
     * 场景信息 String(256)
     * 该字段常用于线下活动时的场景信息上报，支持上报实际门店信息，商户也可以按需求自己上报相关信息。
     * 该字段为JSON对象数据，对象格式为
     * {"store_info":{"id": "门店ID","name": "名称","area_code": "编码","address": "地址" }}
     * ，字段详细说明请点击行前的+展开
     */
    var sceneInfo: SceneInfo?= null
    /**
     * 商户ApiKey
     */
    var apiKey: String = ""
}