package com.zhipuchina.wxpay.utils

public fun ByteArray.toHexString(): String {
    return StringBuilder().run {
        this@toHexString.forEach {
            this.append(Integer.toHexString((it.toInt() and 0xFF or 0x100)).substring(1, 3))
        }
        this.toString().toUpperCase()
    }
}
