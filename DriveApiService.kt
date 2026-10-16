package com.hawkins.library.data.remote

import com.hawkins.library.data.model.DriveFileListResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface DriveApiService {

    @GET("drive/v3/files")
    suspend fun listFiles(
        @Query("q") query: String,
        @Query("pageSize") pageSize: Int = 1000,
        @Query("pageToken") pageToken: String? = null,
        @Query("fields") fields: String = DEFAULT_FIELDS,
        @Query("orderBy") orderBy: String = "name_natural",
        @Query("key") apiKey: String
    ): DriveFileListResponse

    companion object {
        const val BASE_URL = "https://www.googleapis.com/"
        const val DEFAULT_FIELDS =
            "files(id,name,mimeType,thumbnailLink,videoMediaMetadata,size,modifiedTime),nextPageToken"

        fun create(): DriveApiService {
            val client = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .followRedirects(true)
                .followSslRedirects(true)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DriveApiService::class.java)
        }
    }
}
