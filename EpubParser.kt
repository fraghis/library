package com.hawkins.library.ui.books.reader

import android.content.Context
import android.net.Uri
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import java.io.IOException
import java.util.zip.ZipInputStream

class EpubParser {

    data class Chapter(val id: String, val title: String, val content: String)
    data class EpubBook(val title: String, val author: String, val chapters: List<Chapter>)

    fun parse(context: Context, uri: Uri): EpubBook {
        val entries = mutableMapOf<String, ByteArray>()

        val inputStream = when (uri.scheme) {
            "content", "file" -> context.contentResolver.openInputStream(uri)
            else -> null
        } ?: throw IOException("Impossible d'ouvrir le fichier EPUB")

        ZipInputStream(inputStream).use { zip ->
            var entry = zip.nextEntry
            var safety = 0
            while (entry != null && safety < 5000) {
                if (!entry.isDirectory) entries[entry.name] = zip.readBytes()
                zip.closeEntry()
                entry = zip.nextEntry
                safety++
            }
        }

        val containerXml = entries["META-INF/container.xml"]?.toString(Charsets.UTF_8)
            ?: throw IOException("EPUB invalide : container.xml manquant")

        val containerDoc = Jsoup.parse(containerXml, "", Parser.xmlParser())
        val opfPath = containerDoc.select("rootfile").first()?.attr("full-path")
            ?: throw IOException("EPUB invalide : OPF introuvable")

        val opfDir = if (opfPath.contains('/')) opfPath.substringBeforeLast('/') else ""
        val opfContent = entries[opfPath]?.toString(Charsets.UTF_8)
            ?: throw IOException("EPUB invalide : OPF introuvable")

        val opfDoc = Jsoup.parse(opfContent, "", Parser.xmlParser())
        val title = opfDoc.select("metadata title").text().ifBlank { "Sans titre" }
        val author = opfDoc.select("metadata creator").text().ifBlank { "Auteur inconnu"
