package com.example.finalcomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = lightColorScheme()) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CampusLifeApp()
                }
            }
        }
    }
}

@Composable
fun CampusLifeApp() {
    var energy by remember { mutableStateOf(60) }
    var stress by remember { mutableStateOf(40) }
    var happiness by remember { mutableStateOf(50) }

    val logs = remember { mutableStateListOf<String>() }

    fun applyAction(action: CampusAction) {
        energy = (energy + action.energy).coerceIn(0, 100)
        stress = (stress + action.stress).coerceIn(0, 100)
        happiness = (happiness + action.happiness).coerceIn(0, 100)
        logs.add(action.log)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Campus Life Simulator", fontSize = 26.sp)

        StatusPanel(energy, stress, happiness)

        ActionPanel { applyAction(it) }

        Divider()

        Text("오늘의 기록", fontSize = 18.sp)
        LogPanel(logs)
    }
}

@Composable
fun StatusPanel(energy: Int, stress: Int, happiness: Int) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("에너지 $energy")
            LinearProgressIndicator(progress = energy / 100f)
            Spacer(Modifier.height(6.dp))

            Text("스트레스 $stress")
            LinearProgressIndicator(progress = stress / 100f)
            Spacer(Modifier.height(6.dp))

            Text("행복도 $happiness")
            LinearProgressIndicator(progress = happiness / 100f)
        }
    }
}

@Composable
fun ActionPanel(onAction: (CampusAction) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("행동 선택", fontSize = 18.sp)

        ActionButton("전공 공부") {
            onAction(CampusAction(-15, +25, -5, "전공 공부를 했다"))
        }

        ActionButton("팀플 회의") {
            onAction(CampusAction(-10, +20, -5, "팀플 회의를 했다"))
        }

        ActionButton("과제 마감") {
            onAction(CampusAction(-25, +35, -15, "과제를 마감했다"))
        }

        ActionButton("헬스장 운동") {
            onAction(CampusAction(-15, -20, +20, "운동으로 스트레스를 풀었다"))
        }

        ActionButton("늦잠 자기") {
            onAction(CampusAction(+25, -15, +10, "늦잠을 자며 회복했다"))
        }

        ActionButton("친구랑 술약속") {
            onAction(CampusAction(-30, +5, +25, "친구들과 술자리를 가졌다"))
        }

        ActionButton("혼자 넷플릭스") {
            onAction(CampusAction(+10, -15, +15, "혼자 콘텐츠를 보며 쉬었다"))
        }

        ActionButton("알바 근무") {
            onAction(CampusAction(-25, +15, -5, "알바를 다녀왔다"))
        }

        ActionButton("산책") {
            onAction(CampusAction(-5, -20, +15, "가볍게 산책했다"))
        }

        ActionButton("밤샘") {
            onAction(CampusAction(-40, +45, -25, "밤을 새웠다"))
        }
    }
}

@Composable
fun ActionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text)
    }
}

@Composable
fun LogPanel(logs: List<String>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(logs) { log ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Text(text = log, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

data class CampusAction(
    val energy: Int,
    val stress: Int,
    val happiness: Int,
    val log: String
)

@Preview(showBackground = true)
@Composable
fun PreviewCampusLife() {
    MaterialTheme {
        CampusLifeApp()
    }
}

