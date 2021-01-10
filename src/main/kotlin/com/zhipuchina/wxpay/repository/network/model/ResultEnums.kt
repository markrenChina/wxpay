package com.zhipuchina.wxpay.repository.network.model

/**
 * 返回状态枚举类
 * @author markrenChina
 */
enum class ResultEnums(var code:Int, var msg:String) {
    SUCCESS(200, "成功"),
    SYSTEM_ERROR(500, "系统繁忙，请稍后再试"),
}