package com.roy.downloader.core.model.data

import java.util.UUID

class PieceResult(
    var infoId: UUID,
    var pieceIndex: Int
) {
    @JvmField
    var retryAfter: Long = 0
}
