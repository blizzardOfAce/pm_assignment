package com.example.pupilmeshassignment.presentation.components


import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import com.example.pupilmeshassignment.utils.FaceDetectionState

@Composable
fun FaceOverlay(
    state: FaceDetectionState,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        // Draw reference rectangle with color based on face detection
        val rectangleColor = if (state.isFaceWithinReference) Color.Green else Color.Red

        drawRect(
            color = rectangleColor,
            topLeft = Offset(state.referenceRect.left, state.referenceRect.top),
            size = Size(state.referenceRect.width, state.referenceRect.height),
            style = Stroke(width = 3.dp.toPx())
        )
    }
}