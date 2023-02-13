package moe.lz233.googleglass.cloudmusic.logic.model.meta

import com.google.gson.annotations.SerializedName

data class User(val userId: Long,
                @SerializedName("nickname") val nickName: String,
                val avatarUrl: String,
                val backgroundUrl:String,
                @SerializedName("signature") val bio: String?,
                val userType: Int)
