package moe.lz233.googleglass.cloudmusic.logic.network.interceptor

import moe.lz233.googleglass.util.LogUtil
import okhttp3.logging.HttpLoggingInterceptor

class HttpLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        LogUtil.d(message)
    }
}