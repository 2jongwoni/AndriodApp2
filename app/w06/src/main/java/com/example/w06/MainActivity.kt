package com.example.w06

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.w06.ui.theme.DksekrudTheme
import kotlinx.coroutines.delay
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

// 데이터 클래스
data class Bubble(
    val id: Int,
    var position: Offset,
    val radius: Float,
    val color: Color,
    val creationTime: Long = System.currentTimeMillis()
)

// 게임 상태
class GameState {
    var bubbles by mutableStateOf<List<Bubble>>(emptyList())
    var score by mutableStateOf(0)
    var isGameOver by mutableStateOf(false)
    var gameTime by mutableStateOf(30)
}

// MainActivity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DksekrudTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BubbleGameScreen()
                }
            }
        }
    }
}

// 게임 화면
@Composable
fun BubbleGameScreen(gameState: GameState = remember { GameState() }) {
    val context = LocalContext.current
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vm = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vm.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    // 스텝1: 버블 생성 및 터치
    LaunchedEffect(gameState.isGameOver) {
        while (!gameState.isGameOver) {
            delay(1000)
            val currentTime = System.currentTimeMillis()
            gameState.bubbles = gameState.bubbles.filter { currentTime - it.creationTime < 3000 }
        }
    }

    // 스텝2: 게임 타이머
    LaunchedEffect(gameState.isGameOver) {
        while (!gameState.isGameOver && gameState.gameTime > 0) {
            delay(1000)
            gameState.gameTime--
        }
        if (gameState.gameTime <= 0) gameState.isGameOver = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        gameState.bubbles.reversed().forEach { bubble ->
                            val distance = sqrt((offset.x - bubble.position.x).pow(2) + (offset.y - bubble.position.y).pow(2))

                                return@detectTapGestures
                            }
                        }

                }
        ) {
            // Canvas 크기 기반 버블 생성
            if (Random.nextFloat() < 0.1f && gameState.bubbles.size < 15) {
                val newBubble = Bubble(
                    id = Random.nextInt(),
                    position = Offset(
                        x = Random.nextFloat() * size.width,
                        y = Random.nextFloat() * size.height
                    ),
                    radius = Random.nextFloat() * 50 + 50,
                    color = Color(
                        red = Random.nextInt(256),
                        green = Random.nextInt(256),
                        blue = Random.nextInt(256),
                        alpha = 200
                    )
                )
                gameState.bubbles = gameState.bubbles + newBubble
            }

            // 버블 그리기
            gameState.bubbles.forEach { bubble ->
                drawCircle(bubble.color, bubble.radius, bubble.position)
            }
        }

        // 점수 표시
        Text(
            text = "Score: ${gameState.score}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        )

        // 남은 시간 표시
        Text(
            text = "Time: ${gameState.gameTime}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        )

        // 게임 오버 다이얼로그
        if (gameState.isGameOver) {
            AlertDialog(
                onDismissRequest = {},
                title = { Text("게임 종료!") },
                text = { Text("최종 점수: ${gameState.score}") },
                confirmButton = {
                    TextButton(onClick = {
                        gameState.score = 0
                        gameState.bubbles = emptyList()
                        gameState.gameTime = 30
                        gameState.isGameOver = false
                    }) {
                        Text("다시 시작")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BubbleGameScreenPreview() {
    DksekrudTheme {
        BubbleGameScreen()
    }
}
