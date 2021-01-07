package com.zhipuchina.wxpay.repository.network.model

data class SceneInfo (
    /**
     * 门店id String(32)
     * 门店编号，由商户自定义
     */
    var id: String="",
    /**
     * 门店名称 String(64)
     */
    var name: String?= null,
    /**
     * 门店行政区划码 String(6)
     *
     */
    var areaCode: String?= null,
    /**
     * 门店详细地址 String(128)
     */
    var address: String?= null
        )