package com.example.pupilmeshassignment.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pupilmeshassignment.R

//Can also be used when implementing a Sign Up Screen:
@Composable
fun AuthComponent(modifier: Modifier, text: String, use: String) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = "Zenithra", style = MaterialTheme.typography.titleMedium, fontSize = 20.sp)
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium,
            fontSize = 36.sp
        )

        Text(text = "Please enter your details to $use", style = MaterialTheme.typography.bodySmall)
        Row(
            modifier = modifier.padding(horizontal = 8.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    .clickable {}
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google_logo),
                    contentDescription = "google logo"
                )
            }
            Box(
                modifier = modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    .clickable {}
            ) {
                Image(
                    painter = painterResource(id = R.drawable.apple_logo),
                    contentDescription = "google logo"
                )
            }
        }
        Text(text = "OR", style = MaterialTheme.typography.bodyMedium)
    }
}