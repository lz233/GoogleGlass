package moe.lz233.googleglass.cloudmusic.logic.network.interceptor

import moe.lz233.googleglass.cloudmusic.utils.ktx.processEapi
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.processEapi()
        response.headers("Set-Cookie").forEach {
            if (it.contains("MUSIC_U"))
                moe.lz233.googleglass.cloudmusic.logic.dao.UserDao.cookie = it.substring(it.indexOf("MUSIC_U=") + 8, it.indexOf(';'))
        }
        return response
    }
}