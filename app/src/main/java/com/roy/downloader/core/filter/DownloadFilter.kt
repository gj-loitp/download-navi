package com.roy.downloader.core.filter

import com.roy.downloader.core.model.data.entity.InfoAndPieces
import io.reactivex.functions.Predicate

interface DownloadFilter : Predicate<InfoAndPieces?>
