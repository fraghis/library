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
    val addedAt: Long = System.currentTimeMillis(),
    val lastPage: Int = 0
) {
    val typeEnum: BookType
        get() = try { BookType.valueOf(type) } catch (_: Exception) { BookType.PDF }
}

enum class BookType { PDF, EPUB, LINK }
