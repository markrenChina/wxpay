package com.zhipuchina.wxpay.repository.network

object HttpHelper {
    /**
     * Unicode转中文
     *
     * @param unicodeStr
     * @return
     */
    fun uniCodeToCN(unicodeStr: String?): String? {
        if (unicodeStr == null) {
            return null
        }
        val stringBuffer = StringBuffer()
        val maxLoop = unicodeStr.length
        var i = 0
        while (i < maxLoop) {
            if (unicodeStr[i] == '\\') {
                val flag = i < maxLoop - 5 && (unicodeStr[i + 1] == 'u' || unicodeStr[i + 1] == 'U')
                if (flag) {
                    try {
                        stringBuffer.append(unicodeStr.substring(i + 2, i + 6).toInt(16).toChar())
                        i += 5
                    } catch (localNumberFormatException: NumberFormatException) {
                        stringBuffer.append(unicodeStr[i])
                    }
                } else {
                    stringBuffer.append(unicodeStr[i])
                }
            } else {
                stringBuffer.append(unicodeStr[i])
            }
            i++
        }
        return stringBuffer.toString()
    }
}