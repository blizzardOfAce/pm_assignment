package com.example.pupilmeshassignment.presentation.screens.facerecognition

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.pupilmeshassignment.utils.FaceDetectorHelper
import org.koin.androidx.compose.koinViewModel
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.pupilmeshassignment.presentation.components.CameraPreview
import com.example.pupilmeshassignment.presentation.components.FaceOverlay

@Composable
fun FaceDetectionScreen(viewModel: FaceDetectionViewModel = koinViewModel()) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    // Track if we have camera permission
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Permission request launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasCameraPermission = isGranted
        }
    )

    // Request camera permission on launch if not already granted
    LaunchedEffect(key1 = true) {
        if (!hasCameraPermission) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.roundToPx() }
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.roundToPx() }

    // Only show camera preview if permission is granted
    if (hasCameraPermission) {
        // Causes scaling issues(rectangle becomes small)
        var screenWidth by remember { mutableStateOf(0) }
        var screenHeight by remember { mutableStateOf(0) }

        val faceDetectorHelper = remember {

            FaceDetectorHelper(context) { rects, imageWidth, imageHeight ->
                viewModel.updateFaceDetections(rects, imageWidth, imageHeight, screenWidthPx, screenHeightPx)
            }
//            FaceDetectorHelper(context) { rects, width, height ->
//                screenWidth = width
//                screenHeight = height
//                viewModel.updateFaceDetections(rects, screenWidthPx, screenHeightPx)
//               // viewModel.updateFaceDetections(rects, width, height)
//            }
        }

        // Cleanup when the composable is disposed
        DisposableEffect(key1 = faceDetectorHelper) {
            onDispose {
                faceDetectorHelper.close()
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            CameraPreview(
                modifier = Modifier.fillMaxSize(),
                onImageCaptured = { bitmap ->
                    faceDetectorHelper.detectAsync(bitmap, System.currentTimeMillis())
                }
            )

            FaceOverlay(
                state = state,
                modifier = Modifier.fillMaxSize()
            )
        }
    } else {
        // Show a message when permission is not granted
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Camera permission is required for face detection")
        }
    }
}
