package com.roy.downloader.core.utils

import android.webkit.MimeTypeMap
import java.util.Locale

object MimeTypeUtils {
    const val DEFAULT_MIME_TYPE = "*/*"
    const val MIME_TYPE_DELIMITER = "/"

    @JvmStatic
    fun getCategory(mime: String?): Category {
        var mime = mime ?: return Category.OTHER
        mime = mime.lowercase(Locale.getDefault())
        if (mime.startsWith("video/")) return Category.VIDEO else if (mime.startsWith("audio/")) return Category.AUDIO else if (mime.startsWith(
                "image/"
            )
        ) return Category.IMAGE else if (mime.startsWith("text/")) return Category.DOCUMENT
        var category: Category?
        return if (mimeToCategory[mime].also {
                category = it
            } == null) Category.OTHER else category!!
    }

    private val mimeToCategory = HashMap<String, Category>()

    init {
        mimeToCategory["application/atom+xml"] = Category.DOCUMENT
        mimeToCategory["application/ecmascript"] = Category.DOCUMENT
        mimeToCategory["application/epub+zip"] = Category.DOCUMENT
        mimeToCategory["application/gpx+xml"] = Category.DOCUMENT
        mimeToCategory["application/gzip"] =
            Category.ARCHIVE
        mimeToCategory["application/hta"] = Category.DOCUMENT
        mimeToCategory["application/java-archive"] = Category.ARCHIVE
        mimeToCategory["application/javascript"] =
            Category.DOCUMENT
        mimeToCategory["application/x-javascript"] =
            Category.DOCUMENT
        mimeToCategory["application/json"] =
            Category.DOCUMENT
        mimeToCategory["application/mpegurl"] =
            Category.VIDEO
        mimeToCategory["application/msword"] =
            Category.DOCUMENT
        mimeToCategory["application/ogg"] =
            Category.VIDEO
        mimeToCategory["application/olescript"] =
            Category.DOCUMENT
        mimeToCategory["application/onenote"] =
            Category.DOCUMENT
        mimeToCategory["application/opensearchdescription+xml"] =
            Category.DOCUMENT
        mimeToCategory["application/pdf"] =
            Category.DOCUMENT
        mimeToCategory["application/postscript"] = Category.DOCUMENT
        mimeToCategory["application/rtf"] =
            Category.DOCUMENT
        mimeToCategory["application/typescript"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.adobe.air-application-installer-package+zip"] =
            Category.ARCHIVE
        mimeToCategory["application/vnd.amazon.ebook"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.android.package-archive"] =
            Category.APK
        mimeToCategory["application/vnd.apple.mpegurl"] = Category.VIDEO
        mimeToCategory["application/vnd.apple.mpegurl.audio"] =
            Category.AUDIO
        mimeToCategory["application/vnd.fdf"] = Category.DOCUMENT
        mimeToCategory["application/vnd.mozilla.xul+xml"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.ms-cab-compressed"] = Category.ARCHIVE
        mimeToCategory["application/vnd.ms-excel"] = Category.DOCUMENT
        mimeToCategory["application/vnd.ms-excel.addin.macroEnabled.12"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.ms-excel.sheet.binary.macroEnabled.12"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.ms-excel.sheet.macroEnabled.12"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.ms-excel.template.macroEnabled.12"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.ms-mediapackage"] = Category.IMAGE
        mimeToCategory["application/vnd.ms-powerpoint"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.ms-powerpoint.addin.macroEnabled.12"] = Category.DOCUMENT
        mimeToCategory["application/vnd.ms-powerpoint.presentation.macroEnabled.12"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.ms-powerpoint.slide.macroEnabled.12"] = Category.DOCUMENT
        mimeToCategory["application/vnd.ms-powerpoint.slideshow.macroEnabled.12"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.ms-powerpoint.template.macroEnabled.12"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.ms-project"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.ms-visio.viewer"] = Category.DOCUMENT
        mimeToCategory["application/vnd.ms-word.document.macroEnabled.12"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.ms-word.template.macroEnabled.12"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.ms-wpl"] = Category.DOCUMENT
        mimeToCategory["application/vnd.ms-xpsdocument"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.oasis.opendocument.chart"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.oasis.opendocument.database"] = Category.DOCUMENT
        mimeToCategory["application/vnd.oasis.opendocument.formula"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.oasis.opendocument.graphics"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.oasis.opendocument.graphics-template"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.oasis.opendocument.image"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.oasis.opendocument.presentation"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.oasis.opendocument.presentation-template"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.oasis.opendocument.spreadsheet"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.oasis.opendocument.spreadsheet-template"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.oasis.opendocument.text"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.oasis.opendocument.text-master"] = Category.DOCUMENT
        mimeToCategory["application/vnd.oasis.opendocument.text-template"] = Category.DOCUMENT
        mimeToCategory["application/vnd.oasis.opendocument.text-web"] = Category.DOCUMENT
        mimeToCategory["application/vnd.openofficeorg.extension"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.openxmlformats-officedocument.presentationml.presentation"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.openxmlformats-officedocument.presentationml.slide"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.openxmlformats-officedocument.presentationml.slideshow"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.openxmlformats-officedocument.presentationml.template"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.openxmlformats-officedocument.spreadsheetml.template"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.openxmlformats-officedocument.wordprocessingml.document"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.openxmlformats-officedocument.wordprocessingml.template"] =
            Category.DOCUMENT
        mimeToCategory["application/vnd.rn-realmedia"] =
            Category.VIDEO
        mimeToCategory["application/vnd.symbian.install"] =
            Category.ARCHIVE
        mimeToCategory["application/vnd.visio"] = Category.DOCUMENT
        mimeToCategory["application/vnd.wap.wmlc"] = Category.DOCUMENT
        mimeToCategory["application/vnd.wap.wmlscriptc"] = Category.DOCUMENT
        mimeToCategory["application/vsix"] = Category.ARCHIVE
        mimeToCategory["application/windows-library+xml"] =
            Category.DOCUMENT
        mimeToCategory["application/windows-search-connector+xml"] = Category.DOCUMENT
        mimeToCategory["application/x-7z-compressed"] = Category.ARCHIVE
        mimeToCategory["application/x-abiword"] =
            Category.DOCUMENT
        mimeToCategory["application/x-ace-compressed"] = Category.ARCHIVE
        mimeToCategory["application/x-astrotite-afa"] =
            Category.ARCHIVE
        mimeToCategory["application/x-alz-compressed"] = Category.ARCHIVE
        mimeToCategory["application/x-apple-diskimage"] =
            Category.ARCHIVE
        mimeToCategory["application/x-arj"] = Category.ARCHIVE
        mimeToCategory["application/x-b1"] = Category.ARCHIVE
        mimeToCategory["application/x-bzip"] =
            Category.ARCHIVE
        mimeToCategory["application/x-bzip2"] =
            Category.ARCHIVE
        mimeToCategory["application/x-cfs-compressed"] =
            Category.ARCHIVE
        mimeToCategory["application/x-compress"] =
            Category.ARCHIVE
        mimeToCategory["application/x-compressed"] =
            Category.ARCHIVE
        mimeToCategory["application/x-cpio"] =
            Category.ARCHIVE
        mimeToCategory["application/x-csh"] =
            Category.ARCHIVE
        mimeToCategory["application/x-dar"] =
            Category.ARCHIVE
        mimeToCategory["application/x-dgc-compressed"] = Category.ARCHIVE
        mimeToCategory["application/x-director"] = Category.VIDEO
        mimeToCategory["application/x-dvi"] = Category.DOCUMENT
        mimeToCategory["application/x-gtar"] = Category.ARCHIVE
        mimeToCategory["application/x-gzip"] = Category.ARCHIVE
        mimeToCategory["application/x-itunes-itlp"] =
            Category.DOCUMENT
        mimeToCategory["application/x-gca-compressed"] =
            Category.ARCHIVE
        mimeToCategory["application/x-latex"] =
            Category.DOCUMENT
        mimeToCategory["application/x-lzip"] = Category.ARCHIVE
        mimeToCategory["application/x-lzh"] =
            Category.ARCHIVE
        mimeToCategory["application/x-lzx"] = Category.ARCHIVE
        mimeToCategory["application/x-lzma"] =
            Category.ARCHIVE
        mimeToCategory["application/x-lzop"] = Category.ARCHIVE
        mimeToCategory["application/x-mpegurl"] = Category.VIDEO
        mimeToCategory["application/x-quicktimeplayer"] = Category.VIDEO
        mimeToCategory["application/x-rar-compressed"] = Category.ARCHIVE
        mimeToCategory["application/x-rar"] =
            Category.ARCHIVE
        mimeToCategory["application/rar"] = Category.ARCHIVE
        mimeToCategory["application/vnd.rar"] = Category.ARCHIVE
        mimeToCategory["application/x-sbx"] =
            Category.ARCHIVE
        mimeToCategory["application/x-sh"] = Category.DOCUMENT
        mimeToCategory["application/x-shar"] = Category.ARCHIVE
        mimeToCategory["application/x-shockwave-flash"] = Category.VIDEO
        mimeToCategory["application/x-silverlight-app"] = Category.ARCHIVE
        mimeToCategory["application/x-smaf"] =
            Category.AUDIO
        mimeToCategory["application/x-snappy-framed"] =
            Category.ARCHIVE
        mimeToCategory["application/x-stuffit"] =
            Category.ARCHIVE
        mimeToCategory["application/x-stuffitx"] = Category.ARCHIVE
        mimeToCategory["application/x-sv4cpio"] =
            Category.ARCHIVE
        mimeToCategory["application/x-tar"] =
            Category.ARCHIVE
        mimeToCategory["application/x-tcl"] =
            Category.DOCUMENT
        mimeToCategory["application/x-tex"] =
            Category.DOCUMENT
        mimeToCategory["application/x-texinfo"] =
            Category.DOCUMENT
        mimeToCategory["application/x-troff"] = Category.DOCUMENT
        mimeToCategory["application/x-troff-man"] =
            Category.DOCUMENT
        mimeToCategory["application/x-troff-me"] =
            Category.DOCUMENT
        mimeToCategory["application/x-troff-ms"] =
            Category.DOCUMENT
        mimeToCategory["application/x-ustar"] = Category.ARCHIVE
        mimeToCategory["application/xaml+xml"] =
            Category.DOCUMENT
        mimeToCategory["application/xapk-package-archive"] =
            Category.APK
        mimeToCategory["application/xhtml+xml"] =
            Category.DOCUMENT
        mimeToCategory["application/xml"] = Category.DOCUMENT
        mimeToCategory["application/xml-dtd"] = Category.DOCUMENT
        mimeToCategory["application/xspf+xml"] = Category.DOCUMENT
        mimeToCategory["application/x-xz"] = Category.ARCHIVE
        mimeToCategory["application/zip"] = Category.ARCHIVE
        mimeToCategory["application/x-zoo"] =
            Category.ARCHIVE
        mimeToCategory["application/php"] =
            Category.DOCUMENT
        mimeToCategory["application/x-php"] =
            Category.DOCUMENT
        mimeToCategory["application/x-httpd-php"] =
            Category.DOCUMENT
        mimeToCategory["application/x-httpd-php-source"] = Category.DOCUMENT
    }

    private val extensionToMime = HashMap<String, String>()

    init {
        extensionToMime["php"] = "application/php"
        extensionToMime["json"] = "application/json"
    }

    private val mimeToExtension = HashMap<String, String>()

    init {
        mimeToExtension["text/php"] = "php"
        mimeToExtension["text/x-php"] = "php"
        mimeToExtension["application/php"] = "php"
        mimeToExtension["application/x-php"] = "php"
        mimeToExtension["application/x-httpd-php"] = "php"
        mimeToExtension["application/x-httpd-php-source"] = "php"
        mimeToExtension["application/json"] = "json"
    }

    @JvmStatic
    fun getExtensionFromMimeType(mimeType: String): String? {
        val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
        return if (extension == null || "bin" == mimeType) mimeToExtension[mimeType] else extension
    }

    @JvmStatic
    fun getMimeTypeFromExtension(extension: String): String? {
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        return if (mimeType == null || "application/octet-stream" == mimeType) extensionToMime[extension] else mimeType
    }

    enum class Category {
        OTHER, ARCHIVE, VIDEO, AUDIO, IMAGE, DOCUMENT, APK
    }
}
