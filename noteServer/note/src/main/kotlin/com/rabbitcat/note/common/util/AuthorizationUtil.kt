package com.rabbitcat.note.common.util

import com.rabbitcat.note.exception.UnsupportedAuthorizationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.encrypt.BytesEncryptor
import org.springframework.util.Base64Utils

object AuthorizationUtil {
    fun getUserIdAndPasswordFromToken(token: String): Array<String> {
        var tokenSplit = token.split(" ")

        val credential =  tokenSplit[1]
        val type = tokenSplit[0]

        if("Basic".equals(type, true)){
            val decoded = String(Base64Utils.decodeFromString(credential))
            return decoded.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }else{
            throw UnsupportedAuthorizationException()
        }
    }

    fun getUserIdFromToken(token: String): String {
        var tokenSplit = token.split(" ")

        val credential =  tokenSplit[1]
        val type = tokenSplit[0]

        if("Basic".equals(type, true)){
            val decoded = String(Base64Utils.decodeFromString(credential))
            return decoded.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        }else{
            throw UnsupportedAuthorizationException()
        }
    }
}