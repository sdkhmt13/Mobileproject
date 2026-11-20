package com.example.mobileproject.ui.components

import com.example.mobileproject.data.ReadingStatus

fun ReadingStatus.label(): String = when (this) {
    ReadingStatus.WANT -> "Want"
    ReadingStatus.READING -> "Reading"
    ReadingStatus.DONE -> "Done"
}