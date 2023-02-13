package moe.lz233.googleglass.cloudmusic.logic.model.response

import moe.lz233.googleglass.cloudmusic.logic.model.meta.PlayList

data class PlaylistDetailResponse(val code: Int, val playlist: PlayList)

data class ModifyPlayListTracksResponse(val code: Int, val message: String?)