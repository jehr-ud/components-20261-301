package com.ud.connect4ude.utils

class Encode{
    fun encodeBase62(value: Long): String {
        val BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"

        var num = value
        val sb = StringBuilder()

        while (num > 0) {
            sb.append(BASE62[(num % 62).toInt()])
            num /= 62
        }

        return sb.reverse().toString()
    }
}