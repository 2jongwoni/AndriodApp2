package com.example.todo_list01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class PlanType {
    COMPANY, CLUB, GAME, LIST
}

data class PlanData(
    val type: PlanType,
    val content: Map<String, String>
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanningApp()
        }
    }
}

@Composable
fun PlanningApp() {
    var savedPlans by remember { mutableStateOf(mutableListOf<PlanData>()) }
    var currentScreen by remember { mutableStateOf<PlanType?>(null) }

    when (currentScreen) {
        null -> PlanningHomeScreen(
            onSelect = { selected -> currentScreen = selected }
        )

        PlanType.COMPANY -> PlanEditorScreen(
            title = "회사 기획안",
            formItems = listOf(
                "프로젝트 개요",
                "목적",
                "기대 효과",
                "진행 일정",
                "필요 자원",
                "예산"
            ),
            onSave = { saved ->
                savedPlans = savedPlans.toMutableList().apply {
                    add(PlanData(PlanType.COMPANY, saved))
                }
                currentScreen = null
            },
            onDelete = { currentScreen = null }
        )

        PlanType.CLUB -> PlanEditorScreen(
            title = "동아리 기획안",
            formItems = listOf(
                "활동 목적",
                "활동 내용",
                "필요 인원",
                "진행 계획",
                "예산",
                "기대 효과"
            ),
            onSave = { saved ->
                savedPlans = savedPlans.toMutableList().apply {
                    add(PlanData(PlanType.CLUB, saved))
                }
                currentScreen = null
            },
            onDelete = { currentScreen = null }
        )

        PlanType.GAME -> PlanEditorScreen(
            title = "게임 기획안",
            formItems = listOf(
                "게임 개요",
                "세계관/스토리",
                "핵심 시스템",
                "캐릭터 / 직업",
                "레벨 디자인",
                "수익 모델"
            ),
            onSave = { saved ->
                savedPlans = savedPlans.toMutableList().apply {
                    add(PlanData(PlanType.GAME, saved))
                }
                currentScreen = null
            },
            onDelete = { currentScreen = null }
        )

        PlanType.LIST -> PlanListScreen(
            plans = savedPlans,
            onClick = { },
            onDelete = { index ->
                savedPlans = savedPlans.toMutableList().apply {
                    removeAt(index)
                }
            },
            onBack = { currentScreen = null }
        )
    }
}

@Composable
fun PlanningHomeScreen(onSelect: (PlanType) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text("기획안 종류 선택", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { onSelect(PlanType.COMPANY) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("회사 기획안")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { onSelect(PlanType.CLUB) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("동아리 기획안")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { onSelect(PlanType.GAME) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("게임 기획안")
        }

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = { onSelect(PlanType.LIST) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("저장된 기획안 목록 보기")
        }
    }
}

@Composable
fun PlanEditorScreen(
    title: String,
    formItems: List<String>,
    onSave: (Map<String, String>) -> Unit,
    onDelete: (() -> Unit)? = null
) {
    val contentMap = remember {
        mutableStateMapOf<String, String>().apply {
            formItems.forEach { this[it] = "" }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(title, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(20.dp))

        formItems.forEach { label ->
            OutlinedTextField(
                value = contentMap[label] ?: "",
                onValueChange = { contentMap[label] = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                label = { Text(label) }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row {

            Button(onClick = { onSave(contentMap) }) {
                Text("저장")
            }

            Spacer(modifier = Modifier.width(10.dp))

            onDelete?.let {
                Button(onClick = it) {
                    Text("취소")
                }
            }
        }
    }
}

@Composable
fun PlanListScreen(
    plans: List<PlanData>,
    onClick: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text("저장된 기획안 목록", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(20.dp))

        if (plans.isEmpty()) {
            Text("저장된 기획안이 없습니다.")
        } else {
            LazyColumn {
                itemsIndexed(plans) { index, item ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onClick(index) }
                            .padding(vertical = 8.dp)
                    ) {

                        Text(
                            text = "${index + 1}. ${item.type} 기획안",
                            modifier = Modifier.weight(1f)
                        )

                        Button(onClick = { onDelete(index) }) {
                            Text("삭제")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("뒤로")
        }
    }
}


