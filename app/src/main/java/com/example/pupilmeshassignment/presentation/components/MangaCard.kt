package com.example.pupilmeshassignment.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.pupilmeshassignment.R
import com.example.pupilmeshassignment.data.local.entity.MangaEntity


@Composable
fun MangaCard(
    manga: MangaEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                // Get painter for state tracking
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(manga.imageUrl)
                        .crossfade(true) // Enable crossfade animation
                        .build()
                )

                // Main image with crossfade
                Image(
                    painter = painter,
                    contentDescription = manga.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Show placeholder or error based on painter state
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> {
                        Icon(
                            painter = painterResource(id = R.drawable.book_5_24dp),
                            contentDescription = null,
                            modifier = Modifier.size(60.dp),
                        )
                    }
                    is AsyncImagePainter.State.Error -> {
                        Icon(
                            painter = painterResource(id = R.drawable.error_24dp),
                            contentDescription = null,
                            modifier = Modifier.size(60.dp),
                        )
                    }
                    else -> {} // Success case, image is already visible
                }
            }

        }
    }
}
