package com.hsikkk.delightroom.browser.ui.component

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hsikkk.delightroom.designsystem.theme.DelightroomtestTheme

@Composable
internal fun NoPermissionPlaceholder(
    modifier: Modifier = Modifier,
    onPermissionGranted: () -> Unit,
) {
    //TODO: permission 요청 및 grant 시 동작 연결

    Box(
        modifier = modifier.background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            Text(
                text = "권한이 없어요",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
            
            Button(
                onClick = { /*TODO*/ },
            ){
                Text("권한 요청하기")
            }
        }
    }
}

@Preview(name = "loading placeholder")
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
