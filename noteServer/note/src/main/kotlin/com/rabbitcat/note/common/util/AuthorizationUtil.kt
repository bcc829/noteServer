package com.rabbitcat.note.common.util

import com.rabbitcat.note.exception.UnauthorizedException
import org.springframework.util.Base64Utils

object AuthorizationUtil {
    fun getUserNameAndPasswordFromToken(token: String): Array<String> {
        var tokenSplit = token.split(" ")

        val credential =  tokenSplit[1]
        val type = tokenSplit[0]

        if("Basic".equals(type, true)){
            val decoded = String(Base64Utils.decodeFromString(credential))
            return decoded.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }else{
            throw UnauthorizedException()
        }
    }
}