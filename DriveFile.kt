package com.hawkins.library.data.model

import com.google.gson.annotations.SerializedName

data class DriveFile(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("mimeType") val mimeType: String = "",
    @SerializedName("thumbnailLink") val thumbnailLink: String? = null,
    @SerializedName("videoMediaMetadata") val videoMediaMetadata: VideoMediaMetadata? = null,
    @SerializedName("size") val size: String? = null,
    @SerializedName("modifiedTime") val modifiedTime: String? = null
) {
    val isVideo: Boolean get() = mimeType.startsWith("video/")

    val durationFormatted: String
        get() {
            val millis = videoMediaMetadata?.durationMillis ?: return ""
            val totalSeconds = millis / 1000
            val hours = totalSeconds / 3600
            val minutes = (totalSeconds % 3600) / 60
            val seconds = totalSeconds % 60
            return if (hours > 0) String.format("%d:%02d:%02d", hours, minutes, seconds)
            else String.format("%d:%02d", minutes, seconds)
        }
}

data class VideoMediaMetadata(
    @SerializedName("width") val width: Int? = null,
    @SerializedName("height") val height: Int? = null,
    @SerializedName("durationMillis") val durationMillis: Long? = null
)

data class DriveFileListResponse(
    @SerializedName("files") val files: List<DriveFile>? = null,
    @SerializedName("nextPageToken") val nextPageToken: String? = null
)
