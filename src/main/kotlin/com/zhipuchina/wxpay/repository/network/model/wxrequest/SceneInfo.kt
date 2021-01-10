package com.zhipuchina.wxpay.repository.network.model.wxrequest

import javax.validation.constraints.Size

/**
 * 统一下单场景信息对象
 * @author markrenChina
 */
data class SceneInfo (
    /**
     * 门店id String(32)
     * 门店编号，由商户自定义
     */
    @Size(max = 32,message = "id must be at most 32 characters long")
    var id: String="",
    /**
     * 门店名称 String(64)
     */
    @Size(max = 64,message = "name must be at most 64 characters long")
    var name: String?= null,
    /**
     * 门店行政区划码 String(6)
     *
     */
    @Size(max = 6,message = "areaCode must be at most 6 characters long")
    var areaCode: String?= null,
    /**
     * 门店详细地址 String(128)
     */
    @Size(max = 128,message = "address must be at most 128 characters long")
    var address: String?= null
)