package com.hsikkk.delightroom.browser.ui.component

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.hsikkk.delightroom.designsystem.theme.DelightroomtestTheme

@Composable
internal fun NoPermissionPlaceholder(
    modifier: Modifier = Modifier,
    onPermissionGranted: () -> Unit,
) {
    val context = LocalContext.current

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            onPermissionGranted()
        }
    }

    Box(
        modifier = modifier.background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "권한이 없어요",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )

            Button(
                onClick = {
                    checkAndRequestPermission(
                        context,
                        requestPermissionLauncher,
                        onPermissionGranted,
                    )
                },
            ) {
                Text("권한 요청하기")
            }
        }
    }
}

private fun checkAndRequestPermission(
    context: Context,
    requestPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    onPermissionGranted: () -> Unit
) {
    if (
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_MEDIA_AUDIO,
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_AUDIO)
    } else {
        onPermissionGranted()
    }
}

@Preview(name = "권한 없는 경우 placeholder")
@Composable
private fun NoPermissionPlaceholderPreview(

) {
    DelightroomtestTheme {
        NoPermissionPlaceholder(
            modifier = Modifier.fillMaxSize(),
            onPermissionGranted = {},
        )
    }
}
