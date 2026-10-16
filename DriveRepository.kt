package com.hawkins.library.data.remote

import com.hawkins.library.BuildConfig
import com.hawkins.library.data.model.DriveFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DriveRepository(
    private val api: DriveApiService = DriveApiService.create(),
    private val apiKey: String = BuildConfig.DRIVE_API_KEY,
    private val folderId: String = BuildConfig.DRIVE_FOLDER_ID
) {

    suspend fun listFolderFiles(): List<DriveFile> = withContext(Dispatchers.IO) {
        if (apiKey.isBlank()) {
            throw IllegalStateException(
                "Clé API Google Drive non configurée. " +
                "Ajoutez DRIVE_API_KEY dans local.properties."
            )
        }

        val allFiles = mutableListOf<DriveFile>()
        var pageToken: String? = null
        var pageCount = 0

        do {
            val response = try {
                api.listFiles(
                    query = "'$folderId' in parents and trashed=false",
                    pageSize = 1000,
                    pageToken = pageToken,
                    apiKey = apiKey
                )
            } catch (e: Exception) {
                throw Exception(translateError(e), e)
            }

            response.files?.let { allFiles.addAll(it) }
            pageToken = response.nextPageToken
            pageCount++

            // Sécurité : max 20 pages (20 000 fichiers)
            if (pageCount > 20) break
        } while (pageToken != null)

        allFiles
    }

    fun buildMediaUrl(fileId: String): String {
        return "https://www.googleapis.com/drive/v3/files/$fileId?alt=media&key=$apiKey"
    }

    fun buildFallbackUrl(fileId: String): String {
        return "https://drive.google.com/uc?export=download&id=$fileId&confirm=t"
    }

    fun buildThumbnailUrl(fileId: String, size: Int = 400): String {
        return "https://drive.google.com/thumbnail?id=$fileId&sz=w$size"
    }

    private fun translateError(e: Exception): String {
        val msg = e.message ?: ""
        return when {
            msg.contains("403") || msg.contains("PERMISSION_DENIED") ->
                "Accès refusé par Google Drive. Vérifiez que la clé API est correctement configurée."
            msg.contains("401") || msg.contains("API_KEY_INVALID") ->
                "Clé API invalide. Vérifiez votre clé dans local.properties."
            msg.contains("404") ->
                "Dossier Google Drive introuvable. Vérifiez l'ID du dossier."
            msg.contains("Unable to resolve host") || msg.contains("Failed to connect") ->
                "Pas de connexion Internet. Vérifiez votre réseau."
            msg.contains("timeout") || msg.contains("timed out") ->
                "Le serveur Google ne répond pas. Réessayez plus tard."
            else ->
                "Erreur de chargement : ${e.message ?: "erreur inconnue"}"
        }
    }
}
