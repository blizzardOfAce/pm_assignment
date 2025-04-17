package com.example.pupilmeshassignment.utils

import androidx.compose.ui.geometry.Rect as ComposeRect


data class FaceDetection(
    val boundingBox: android.graphics.Rect,
    val isWithinReference: Boolean
)

data class FaceDetectionState(
    val faceDetections: List<FaceDetection> = emptyList(),
    val referenceRect: ComposeRect = ComposeRect.Zero,
    val isFaceWithinReference: Boolean = false
)
