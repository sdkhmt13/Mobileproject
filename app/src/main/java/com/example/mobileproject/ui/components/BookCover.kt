package com.example.mobileproject.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage

@Composable
fun BookCover(
    url: String?,
    title: String,
    modifier: Modifier = Modifier
) {
    val shapedModifier = modifier.clip(RoundedCornerShape(8.dp))

    if (url == null) {
        CoverPlaceholder(title = title, modifier = shapedModifier)
    } else {
        // Coil downloads the image by URL and caches it
        SubcomposeAsyncImage(
            model = url,
            contentDescription = "Cover of $title",
            contentScale = ContentScale.Crop,
            modifier = shapedModifier,
            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                }
            },
            error = { CoverPlaceholder(title = title, modifier = Modifier.fillMaxSize()) }
        )
    }
}

// Shown when there is no cover URL or the image failed to load
@Composable
private fun CoverPlaceholder(title: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title.take(1).uppercase(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}