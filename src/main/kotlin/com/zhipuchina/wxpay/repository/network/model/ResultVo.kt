package com.zhipuchina.wxpay.repository.network.model

import com.fasterxml.jackson.databind.ObjectMapper

/**
 * 统一返回结构体
 * @author markrenChina
 */
class ResultVo<T>(
    var data:T? = null,
    var status: Int = ResultEnums.OK.code,
    var msg:String = ResultEnums.lookup(status)?.msg ?: ""
) {
    override fun toString(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}