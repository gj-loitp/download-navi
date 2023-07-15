package com.roy.downloader.core.model

import com.roy.downloader.core.model.data.PieceResult
import java.util.concurrent.Callable

internal interface PieceThread : Callable<PieceResult?>
