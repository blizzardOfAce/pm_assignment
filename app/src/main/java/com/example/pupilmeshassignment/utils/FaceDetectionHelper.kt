package com.example.pupilmeshassignment.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.framework.image.MPImage
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.vision.facedetector.FaceDetector
import com.google.mediapipe.tasks.vision.facedetector.FaceDetector.FaceDetectorOptions
import com.google.mediapipe.tasks.vision.facedetector.FaceDetectorResult
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FaceDetectorHelper(
    context: Context,
    private val onResult: (List<Rect>, Int, Int) -> Unit
) {
    private val faceDetector: FaceDetector
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val baseOptions = BaseOptions.builder()
            .setModelAssetPath("face_detection_short_range.tflite")
            .build()

        val options = FaceDetectorOptions.builder()
            .setBaseOptions(baseOptions)
            .setMinDetectionConfidence(0.5f)
            .setRunningMode(RunningMode.LIVE_STREAM)
            .setResultListener(this::onDetectionResult)
            .setErrorListener { error ->
                android.util.Log.e("FaceDetectorHelper", "MediaPipe face detection error: $error")
            }
            .build()

        faceDetector = FaceDetector.createFromOptions(context, options)
    }

    private fun onDetectionResult(result: FaceDetectorResult, inputImage: MPImage) {
        // Convert RectF to Rect
        val boundingBoxes = result.detections().map { detection ->
            val rectF = detection.boundingBox()
            Rect(rectF.left.toInt(), rectF.top.toInt(), rectF.right.toInt(), rectF.bottom.toInt())
        }
        onResult(boundingBoxes, inputImage.width, inputImage.height)
    }

    fun detectAsync(bitmap: Bitmap, timestamp: Long) {
        executorService.execute {
            val mpImage = BitmapImageBuilder(bitmap).build()
            faceDetector.detectAsync(mpImage, timestamp)
        }
    }

    fun close() {
        faceDetector.close()
        executorService.shutdown()
    }
}
