package moe.lz233.googleglass.cloudmusic.utils.ktx

import moe.lz233.googleglass.cloudmusic.logic.dao.UserDao

fun String.adjustParam(width: Int, height: Int) = "$this?param=${width}y${height}"
fun String.isMyFavorite() = this == "${UserDao.name}喜欢的音乐"