package com.example.w03


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.w03.ui.theme.DksekrudTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DksekrudTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFFEBEE) // Material Pink 50
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // 설명 텍스트
        Text(
            text = "아이디",
            fontSize = 17.sp,
            modifier = Modifier.fillMaxWidth()
        )

        // 이메일
        Text(
            text = "jw20050118@gmail.com",
            color = Color(0xFFCFCECE),
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        )

        // 구분선
        Spacer(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFD4D4D3))
        )

        // 비밀번호 입력 필드
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("비밀번호") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        // "비밀번호가 기억나지 않으세요?"
        Text(
            text = "비밀번호가 기억나지 않으세요?",
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        )

        // 확인 버튼
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("확인")
        }
    }
}
