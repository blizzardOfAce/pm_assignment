package com.example.pupilmeshassignment.presentation.screens.facerecognition

import androidx.compose.ui.geometry.Rect as ComposeRect
import androidx.lifecycle.ViewModel
import com.example.pupilmeshassignment.utils.FaceDetection
import com.example.pupilmeshassignment.utils.FaceDetectionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FaceDetectionViewModel : ViewModel() {
    private val _state = MutableStateFlow(FaceDetectionState())
    val state: StateFlow<FaceDetectionState> = _state.asStateFlow()

    fun updateFaceDetections(
        detections: List<android.graphics.Rect>,
        imageWidth: Int,
        imageHeight: Int,
        screenWidth: Int,
        screenHeight: Int
    ) {
        // Reference rect: 50% of screen, centered
        val referenceWidth = screenWidth * 0.75f
        val referenceHeight = screenHeight * 0.35f
        val referenceLeft = (screenWidth - referenceWidth) / 2f
        val referenceTop = (screenHeight - referenceHeight) / 3f

        val referenceRect = ComposeRect(
            left = referenceLeft,
            top = referenceTop,
            right = referenceLeft + referenceWidth,
            bottom = referenceTop + referenceHeight
        )

        // Scale detections to match screen coordinates
        val scaleX = screenWidth.toFloat() / imageWidth * 0.8f
        val scaleY = screenHeight.toFloat() / imageHeight * 0.8f

        val scaledDetections = detections.map { original ->
            android.graphics.Rect(
                (original.left * scaleX).toInt(),
                (original.top * scaleY).toInt(),
                (original.right * scaleX).toInt(),
                (original.bottom * scaleY).toInt()
            )
        }

        val isAnyFaceWithinReference = scaledDetections.any { rect ->
            isRectWithinReference(rect, referenceRect)
        }

        _state.value = FaceDetectionState(
            faceDetections = scaledDetections.map { FaceDetection(it, false) },
            referenceRect = referenceRect,
            isFaceWithinReference = isAnyFaceWithinReference
        )
    }


    private fun isRectWithinReference(rect: android.graphics.Rect, reference: ComposeRect): Boolean {
        // Check if the face is completely inside the reference rectangle
        return rect.left >= reference.left &&
                rect.top >= reference.top &&
                rect.right <= reference.right &&
                rect.bottom <= reference.bottom
    }
}
