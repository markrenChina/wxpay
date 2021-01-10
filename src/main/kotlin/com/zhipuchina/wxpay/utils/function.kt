package com.zhipuchina.wxpay.utils

public fun ByteArray.toHexString(): String {
    val byteArray = this
    return StringBuilder().run {
        byteArray.forEach {
            this.append(Integer.toHexString((it.toInt() and 0xFF or 0x100)).substring(1, 3))
        }
        this.toString().toUpperCase()
    }
}
