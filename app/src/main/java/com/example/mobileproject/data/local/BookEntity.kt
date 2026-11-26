package com.example.mobileproject.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mobileproject.data.Book
import com.example.mobileproject.data.ReadingStatus

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val id: String,
    val title: String,
    val author: String,
    val description: String,
    val status: ReadingStatus,
    val coverUrl: String?,
    val year: Int?,
    val addedAt: Long = System.currentTimeMillis()
)

fun BookEntity.toBook(): Book = Book(
    id = id,
    title = title,
    author = author,
    description = description,
    status = status,
    coverUrl = coverUrl,
    year = year
)

fun Book.toEntity(): BookEntity = BookEntity(
    id = id,
    title = title,
    author = author,
    description = description,
    status = status,
    coverUrl = coverUrl,
    year = year
)