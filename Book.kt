package com.hawkins.library.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val author: String? = null,
    val type: String = "PDF",
    val uri: String = "",
    val coverPath: String? = null,
    val addedAt: Long = System.currentTimeMillis(),
    val lastReadAt: Long? = null,
    val lastPage: Int = 0,
    val totalPages: Int = 0
) {
    val typeEnum: BookType get() = try { BookType.valueOf(type) } catch (_: Exception) { BookType.PDF }
}

enum class BookType {
    PDF, EPUB, LINK
}

@Entity(tableName = "reading_progress")
data class ReadingProgress(
    @PrimaryKey val bookId: Long,
    val currentChapter: Int = 0,
    val currentPage: Int = 0,
    val scrollPosition: Float = 0f,
    val updatedAt: Long = System.currentTimeMillis()
)
